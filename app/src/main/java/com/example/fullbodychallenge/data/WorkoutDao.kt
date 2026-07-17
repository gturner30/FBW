package com.example.fullbodychallenge.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: WorkoutLog)

    @Query("SELECT * FROM workout_log ORDER BY dateEpochDay DESC")
    fun observeAll(): Flow<List<WorkoutLog>>

    @Query("SELECT * FROM workout_log WHERE dateEpochDay = :epochDay LIMIT 1")
    suspend fun getForDay(epochDay: Long): WorkoutLog?
}
