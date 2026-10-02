package com.example.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.MockTest
import kotlinx.coroutines.flow.Flow

@Dao
interface MockTestDao {
    @Query("SELECT * FROM mock_tests ORDER BY sNo ASC, id ASC")
    fun getAllMockTests(): Flow<List<MockTest>>

    @Query("SELECT * FROM mock_tests WHERE isAttempted = 1 ORDER BY attemptTimestamp ASC, sNo ASC")
    fun getAttemptedMockTests(): Flow<List<MockTest>>

    @Query("SELECT COUNT(*) FROM mock_tests")
    suspend fun getMockTestCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tests: List<MockTest>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(test: MockTest): Long

    @Update
    suspend fun update(test: MockTest)

    @Delete
    suspend fun delete(test: MockTest)
}
