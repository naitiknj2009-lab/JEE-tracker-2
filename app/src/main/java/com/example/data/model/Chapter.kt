package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class Chapter(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sNo: Int,
    val name: String,
    val subject: Subject,
    val category: String, // e.g. "Mechanics", "Calculus", "Physical Chemistry"
    val weightage: Weightage = Weightage.MEDIUM,
    
    // 7 Core Checkpoints from Mission 100 Tracker
    val lectureDone: Boolean = false,
    val dppDone: Boolean = false,
    val pyqDone: Boolean = false,
    val sheetDone: Boolean = false,
    val notesDone: Boolean = false,
    val testDone: Boolean = false,
    val analysisDone: Boolean = false,
    
    // Status & Notes
    val remarks: String = "",
    val isBacklog: Boolean = false,
    
    // Spaced Repetition Revision
    val revisionCount: Int = 0,
    val lastRevisedTimestamp: Long = 0L,
    val nextRevisionTimestamp: Long = 0L // Timestamp when revision is scheduled
) {
    val completedStagesCount: Int
        get() = (if (lectureDone) 1 else 0) +
                (if (dppDone) 1 else 0) +
                (if (pyqDone) 1 else 0) +
                (if (sheetDone) 1 else 0) +
                (if (notesDone) 1 else 0) +
                (if (testDone) 1 else 0) +
                (if (analysisDone) 1 else 0)

    val progressPercent: Float
        get() = (completedStagesCount / 7f) * 100f

    val isFullyCompleted: Boolean
        get() = completedStagesCount == 7
}
