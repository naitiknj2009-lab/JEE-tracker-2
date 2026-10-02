package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mistakes")
data class Mistake(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val questionTitle: String,
    val questionDescription: String = "",
    val subject: Subject,
    val chapterName: String,
    val errorReason: String, // "Calculation Error", "Concept Gap", "Misread Question", "Time Pressure", "Formula Forgotten"
    val resolutionNote: String, // Key takeaway / formula
    val flaggedForReattempt: Boolean = true,
    val isMastered: Boolean = false,
    val createdTimestamp: Long = System.currentTimeMillis()
)
