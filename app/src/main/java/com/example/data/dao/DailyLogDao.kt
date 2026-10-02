package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.DailyLog
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyLogDao {
    @Query("SELECT * FROM daily_logs ORDER BY dateString DESC")
    fun getAllLogs(): Flow<List<DailyLog>>

    @Query("SELECT * FROM daily_logs WHERE dateString = :dateString LIMIT 1")
    fun getLogByDate(dateString: String): Flow<DailyLog?>

    @Query("SELECT * FROM daily_logs WHERE dateString = :dateString LIMIT 1")
    suspend fun getLogByDateSync(dateString: String): DailyLog?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(log: DailyLog)

    @Update
    suspend fun update(log: DailyLog)

    @Query("SELECT SUM(studyDurationMinutes) FROM daily_logs")
    fun getTotalStudyMinutes(): Flow<Int?>
}
