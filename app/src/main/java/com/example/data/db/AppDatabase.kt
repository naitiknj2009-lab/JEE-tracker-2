package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.dao.ChapterDao
import com.example.data.dao.DailyLogDao
import com.example.data.dao.MistakeDao
import com.example.data.dao.MockTestDao
import com.example.data.model.Chapter
import com.example.data.model.DailyLog
import com.example.data.model.Mistake
import com.example.data.model.MockTest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Chapter::class, MockTest::class, Mistake::class, DailyLog::class],
    version = 1,
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

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "jee_tracker_database"
                )
                .addCallback(DatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database)
                    }
                }
            }
        }

        suspend fun populateDatabase(database: AppDatabase) {
            val chapterDao = database.chapterDao()
            val mockTestDao = database.mockTestDao()
            val dailyLogDao = database.dailyLogDao()

            if (chapterDao.getChapterCount() == 0) {
                chapterDao.insertAll(InitialData.getAllChapters())
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
