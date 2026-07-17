package com.example.fullbodychallenge.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class WorkoutRepository(private val dao: WorkoutDao) {

    fun observeLogs(): Flow<List<WorkoutLog>> = dao.observeAll()

    /** Current streak in consecutive completed days, counting back from today. */
    fun observeStreak(): Flow<Int> = dao.observeAll().map { logs ->
        val completedDays = logs.filter { it.completed }.map { it.dateEpochDay }.toSet()
        var streak = 0
        var day = LocalDate.now().toEpochDay()
        while (completedDays.contains(day)) {
            streak++
            day--
        }
        streak
    }

    suspend fun logWorkout(date: LocalDate, dayType: DayType, difficulty: Int, completed: Boolean) {
        dao.insert(
            WorkoutLog(
                dateEpochDay = date.toEpochDay(),
                dayType = dayType.name,
                difficulty = difficulty,
                completed = completed
            )
        )
    }

    suspend fun getForToday(): WorkoutLog? = dao.getForDay(LocalDate.now().toEpochDay())
}
