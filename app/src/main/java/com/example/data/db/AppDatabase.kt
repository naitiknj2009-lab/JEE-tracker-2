package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.dao.ChapterDao
import com.example.data.dao.DailyLogDao
import com.example.data.dao.MistakeDao
import com.example.data.dao.MockTestDao
import com.example.data.model.Chapter
import com.example.data.model.DailyLog
import com.example.data.model.Mistake
import com.example.data.model.MockTest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Database(
    entities = [Chapter::class, MockTest::class, Mistake::class, DailyLog::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun chapterDao(): ChapterDao
    abstract fun mockTestDao(): MockTestDao
    abstract fun mistakeDao(): MistakeDao
    abstract fun dailyLogDao(): DailyLogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private val populateMutex = Mutex()

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "jee_tracker_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }

        suspend fun populateDatabase(database: AppDatabase) {
            populateMutex.withLock {
                val chapterDao = database.chapterDao()
                val mockTestDao = database.mockTestDao()
                val dailyLogDao = database.dailyLogDao()

                val currentChapterCount = chapterDao.getChapterCount()
                if (currentChapterCount == 0) {
                    chapterDao.insertAll(InitialData.getAllChapters())
                } else if (currentChapterCount != 79) {
                    // Re-sync cleanly to remove any legacy duplicate entries while preserving user progress
                    val existing = chapterDao.getAllChaptersSync()
                    val existingProgress = existing.associateBy { "${it.subject.name}_${it.sNo}" }
                    val cleanList = InitialData.getAllChapters().map { initial ->
                        val prev = existingProgress["${initial.subject.name}_${initial.sNo}"]
                        if (prev != null) {
                            initial.copy(
                                lectureDone = prev.lectureDone,
                                dppDone = prev.dppDone,
                                pyqDone = prev.pyqDone,
                                sheetDone = prev.sheetDone,
                                notesDone = prev.notesDone,
                                testDone = prev.testDone,
                                analysisDone = prev.analysisDone,
                                remarks = prev.remarks,
                                isBacklog = prev.isBacklog,
                                revisionCount = prev.revisionCount,
                                lastRevisedTimestamp = prev.lastRevisedTimestamp,
                                nextRevisionTimestamp = prev.nextRevisionTimestamp
                            )
                        } else {
                            initial
                        }
                    }
                    chapterDao.deleteAll()
                    chapterDao.insertAll(cleanList)
                }

                if (mockTestDao.getMockTestCount() == 0) {
                    mockTestDao.insertAll(InitialData.getInitialMockTests())
                }

                // Create initial daily log for today
                val todayStr = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
                if (dailyLogDao.getLogByDateSync(todayStr) == null) {
                    dailyLogDao.insertOrUpdate(
                        DailyLog(
                            dateString = todayStr,
                            studyDurationMinutes = 150,
                            completedText = "Rotational dynamics PYQs + Atomic structure notes",
                            uncompletedText = "Definite integration module practice",
                            distractionReason = "Spent extra time on tough Physics mechanics problem",
                            tomorrowTask1 = "Complete 30 Calculus PYQs",
                            tomorrowTask2 = "Thermodynamics revision",
                            tomorrowTask3 = "Mock Test Analysis",
                            confidenceScore = 8,
                            energyScore = 9,
                            sleepHours = 7.5f,
                            lecturesCount = 2,
                            dppCount = 2,
                            pyqCount = 25,
                            consistencyScore = 9
                        )
                    )
                }
            }
        }
    }
}
