package com.example.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.Mistake
import com.example.data.model.Subject
import kotlinx.coroutines.flow.Flow

@Dao
interface MistakeDao {
    @Query("SELECT * FROM mistakes ORDER BY createdTimestamp DESC")
    fun getAllMistakes(): Flow<List<Mistake>>

    @Query("SELECT * FROM mistakes WHERE subject = :subject ORDER BY createdTimestamp DESC")
    fun getMistakesBySubject(subject: Subject): Flow<List<Mistake>>

    @Query("SELECT * FROM mistakes WHERE flaggedForReattempt = 1 ORDER BY createdTimestamp DESC")
    fun getFlaggedMistakes(): Flow<List<Mistake>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mistake: Mistake): Long

    @Update
    suspend fun update(mistake: Mistake)

    @Delete
    suspend fun delete(mistake: Mistake)
}
