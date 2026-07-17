package com.example.fullbodychallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fullbodychallenge.data.DayType
import com.example.fullbodychallenge.data.ExerciseTemplate
import com.example.fullbodychallenge.data.RestDayPreferences
import com.example.fullbodychallenge.data.WorkoutPlans
import com.example.fullbodychallenge.data.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class WorkoutViewModel(
    private val repository: WorkoutRepository,
    private val restDayPreferences: RestDayPreferences
) : ViewModel() {

    val streak: StateFlow<Int> = repository.observeStreak()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    private val _restDays = MutableStateFlow(restDayPreferences.getRestDays())
    val restDays: StateFlow<Set<Int>> = _restDays

    /** Today's assigned focus, or null if today is a rest day. Not user-selectable. */
    val todaysDayType: StateFlow<DayType?> = _restDays
        .map { rest -> WorkoutPlans.workoutFor(LocalDate.now(), rest) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            WorkoutPlans.workoutFor(LocalDate.now(), _restDays.value)
        )

    private val _difficulty = MutableStateFlow(2)
    val difficulty: StateFlow<Int> = _difficulty

    // exercise id -> completed reps/seconds logged so far this session
    private val _progress = MutableStateFlow<Map<String, Int>>(emptyMap())
    val progress: StateFlow<Map<String, Int>> = _progress

    /** Toggles [isoDayOfWeek] as a rest day. No-ops if already at the 2-day cap and adding a new one. */
    fun toggleRestDay(isoDayOfWeek: Int) {
        val current = _restDays.value
        val updated = when {
            isoDayOfWeek in current -> current - isoDayOfWeek
            current.size >= 2 -> return
            else -> current + isoDayOfWeek
        }
        _restDays.value = updated
        restDayPreferences.setRestDays(updated)
        _progress.value = emptyMap()
    }

    fun selectDifficulty(level: Int) {
        _difficulty.value = level.coerceIn(1, 5)
    }

    fun currentExercises(): List<ExerciseTemplate> =
        todaysDayType.value?.let { WorkoutPlans.plans[it] }.orEmpty()

    fun logProgress(exerciseId: String, amount: Int) {
        _progress.value = _progress.value.toMutableMap().apply {
            put(exerciseId, amount)
        }
    }

    fun isWorkoutComplete(): Boolean {
        val exercises = currentExercises()
        val prog = _progress.value
        return exercises.isNotEmpty() && exercises.all { ex ->
            (prog[ex.id] ?: 0) >= ex.targetForLevel(_difficulty.value) * ex.sets
        }
    }

    fun finishWorkout() {
        val day = todaysDayType.value ?: return
        viewModelScope.launch {
            repository.logWorkout(
                date = LocalDate.now(),
                dayType = day,
                difficulty = _difficulty.value,
                completed = isWorkoutComplete()
            )
        }
    }
}
