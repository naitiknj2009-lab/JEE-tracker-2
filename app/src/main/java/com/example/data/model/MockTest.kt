package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mock_tests")
data class MockTest(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sNo: Int = 0,
    val testName: String,
    val testType: String = "Part Test", // "Part Test", "Full Test", "AITS", "Custom"
    val pattern: String = "JEE Main",
    val platform: String = "Mission 100", // "MathonGo", "Allen", "NTA Abhyas", etc.
    val scheduledDate: String = "",
    val isAttempted: Boolean = false,
    val attemptTimestamp: Long = 0L,
    
    // Subject Marks Breakdown
    val physicsCorrect: Int = 0,
    val physicsIncorrect: Int = 0,
    val physicsUnattempted: Int = 25,
    
    val chemistryCorrect: Int = 0,
    val chemistryIncorrect: Int = 0,
    val chemistryUnattempted: Int = 25,
    
    val mathCorrect: Int = 0,
    val mathIncorrect: Int = 0,
    val mathUnattempted: Int = 25,
    
    // Summary
    val totalScore: Int = 0,
    val maxScore: Int = 300,
    val accuracy: Float = 0f,
    val negativeMarks: Int = 0,
    
    // Analysis
    val mistakeTopics: String = "",
    val isAnalysisDone: Boolean = false
) {
    val totalCorrect: Int get() = physicsCorrect + chemistryCorrect + mathCorrect
    val totalIncorrect: Int get() = physicsIncorrect + chemistryIncorrect + mathIncorrect
    val totalAttempted: Int get() = totalCorrect + totalIncorrect
}
