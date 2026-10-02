package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.Chapter
import com.example.data.model.Subject
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {
    @Query("SELECT * FROM chapters ORDER BY subject ASC, sNo ASC")
    fun getAllChapters(): Flow<List<Chapter>>

    @Query("SELECT * FROM chapters ORDER BY subject ASC, sNo ASC")
    suspend fun getAllChaptersSync(): List<Chapter>

    @Query("SELECT * FROM chapters WHERE subject = :subject ORDER BY sNo ASC")
    fun getChaptersBySubject(subject: Subject): Flow<List<Chapter>>

    @Query("SELECT * FROM chapters WHERE nextRevisionTimestamp > 0 AND nextRevisionTimestamp <= :currentTime ORDER BY nextRevisionTimestamp ASC")
    fun getChaptersDueForRevision(currentTime: Long): Flow<List<Chapter>>

    @Query("SELECT * FROM chapters WHERE id = :id LIMIT 1")
    suspend fun getChapterById(id: Long): Chapter?

    @Query("SELECT COUNT(*) FROM chapters")
    suspend fun getChapterCount(): Int

    @Query("DELETE FROM chapters")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(chapters: List<Chapter>)

    @Update
    suspend fun updateChapter(chapter: Chapter)

    @Query("UPDATE chapters SET remarks = :remarks WHERE id = :id")
    suspend fun updateRemarks(id: Long, remarks: String)

    @Query("UPDATE chapters SET isBacklog = :isBacklog WHERE id = :id")
    suspend fun updateBacklog(id: Long, isBacklog: Boolean)
}
