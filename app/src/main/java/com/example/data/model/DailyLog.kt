package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_logs")
data class DailyLog(
    @PrimaryKey
    val dateString: String, // "YYYY-MM-DD"
    val timestamp: Long = System.currentTimeMillis(),
    
    // Study Timer / Hours
    val studyDurationMinutes: Int = 0,
    
    // PDF Page 5 Daily Check-In fields
    val completedText: String = "",
    val uncompletedText: String = "",
    val distractionReason: String = "",
    val tomorrowTask1: String = "",
    val tomorrowTask2: String = "",
    val tomorrowTask3: String = "",
    
    // PDF Page 6 Confidence & Health Metrics
    val confidenceScore: Int = 8, // /10
    val energyScore: Int = 8,     // /10
    val sleepHours: Float = 7.0f,
    val backlogNotes: String = "",
    
    // Weekly / Quantitative self-check
    val lecturesCount: Int = 0,
    val dppCount: Int = 0,
    val pyqCount: Int = 0,
    val consistencyScore: Int = 8 // /10
)
