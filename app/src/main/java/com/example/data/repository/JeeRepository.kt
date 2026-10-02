package com.example.data.repository

import com.example.data.dao.ChapterDao
import com.example.data.dao.DailyLogDao
import com.example.data.dao.MistakeDao
import com.example.data.dao.MockTestDao
import com.example.data.model.Chapter
import com.example.data.model.DailyLog
import com.example.data.model.Mistake
import com.example.data.model.MockTest
import com.example.data.model.Subject
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

class JeeRepository(
    private val chapterDao: ChapterDao,
    private val mockTestDao: MockTestDao,
    private val mistakeDao: MistakeDao,
    private val dailyLogDao: DailyLogDao
) {
    val allChapters: Flow<List<Chapter>> = chapterDao.getAllChapters()
    fun getChaptersBySubject(subject: Subject): Flow<List<Chapter>> = chapterDao.getChaptersBySubject(subject)
    
    fun getChaptersDueForRevision(currentTime: Long): Flow<List<Chapter>> =
        chapterDao.getChaptersDueForRevision(currentTime)

    suspend fun updateChapter(chapter: Chapter) {
        chapterDao.updateChapter(chapter)
    }

    suspend fun updateRemarks(chapterId: Long, remarks: String) {
        chapterDao.updateRemarks(chapterId, remarks)
    }

    suspend fun scheduleNextRevision(chapter: Chapter) {
        val now = System.currentTimeMillis()
        val nextCount = chapter.revisionCount + 1
        // Spaced repetition: 3 days, 7 days, 21 days, 45 days
        val daysToAdd = when (nextCount) {
            1 -> 3L
            2 -> 7L
            3 -> 21L
            else -> 45L
        }
        val nextTimestamp = now + TimeUnit.DAYS.toMillis(daysToAdd)
        val updated = chapter.copy(
            revisionCount = nextCount,
            lastRevisedTimestamp = now,
            nextRevisionTimestamp = nextTimestamp
        )
        chapterDao.updateChapter(updated)
    }

    // Mock Tests
    val allMockTests: Flow<List<MockTest>> = mockTestDao.getAllMockTests()
    val attemptedMockTests: Flow<List<MockTest>> = mockTestDao.getAttemptedMockTests()

    suspend fun saveMockTest(test: MockTest) {
        if (test.id == 0L) {
            mockTestDao.insert(test)
        } else {
            mockTestDao.update(test)
        }
    }

    suspend fun deleteMockTest(test: MockTest) {
        mockTestDao.delete(test)
    }

    // Mistakes
    val allMistakes: Flow<List<Mistake>> = mistakeDao.getAllMistakes()
    fun getMistakesBySubject(subject: Subject): Flow<List<Mistake>> = mistakeDao.getMistakesBySubject(subject)
    val flaggedMistakes: Flow<List<Mistake>> = mistakeDao.getFlaggedMistakes()

    suspend fun saveMistake(mistake: Mistake) {
        if (mistake.id == 0L) {
            mistakeDao.insert(mistake)
        } else {
            mistakeDao.update(mistake)
        }
    }

    suspend fun deleteMistake(mistake: Mistake) {
        mistakeDao.delete(mistake)
    }

    // Daily Logs
    val allLogs: Flow<List<DailyLog>> = dailyLogDao.getAllLogs()
    fun getLogForDate(dateString: String): Flow<DailyLog?> = dailyLogDao.getLogByDate(dateString)

    suspend fun saveDailyLog(log: DailyLog) {
        dailyLogDao.insertOrUpdate(log)
    }

    suspend fun addStudyMinutes(dateString: String, minutes: Int) {
        val existing = dailyLogDao.getLogByDateSync(dateString)
        if (existing != null) {
            dailyLogDao.update(existing.copy(studyDurationMinutes = existing.studyDurationMinutes + minutes))
        } else {
            dailyLogDao.insertOrUpdate(DailyLog(dateString = dateString, studyDurationMinutes = minutes))
        }
    }
}
