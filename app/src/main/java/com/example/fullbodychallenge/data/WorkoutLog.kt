package com.example.fullbodychallenge.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_log")
data class WorkoutLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateEpochDay: Long,   // LocalDate.toEpochDay()
    val dayType: String,      // DayType.name
    val difficulty: Int,      // 1-5
    val completed: Boolean
)
