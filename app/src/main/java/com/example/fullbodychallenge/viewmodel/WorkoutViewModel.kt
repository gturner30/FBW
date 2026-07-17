package com.example.fullbodychallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fullbodychallenge.data.DayType
import com.example.fullbodychallenge.data.ExerciseTemplate
import com.example.fullbodychallenge.data.WorkoutPlans
import com.example.fullbodychallenge.data.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class WorkoutViewModel(private val repository: WorkoutRepository) : ViewModel() {

    val streak: StateFlow<Int> = repository.observeStreak()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    private val _selectedDayType = MutableStateFlow(
        WorkoutPlans.suggestedDayType(LocalDate.now().dayOfWeek.value)
    )
    val selectedDayType: StateFlow<DayType> = _selectedDayType

    private val _difficulty = MutableStateFlow(2)
    val difficulty: StateFlow<Int> = _difficulty

    // exercise id -> completed reps/seconds logged so far this session
    private val _progress = MutableStateFlow<Map<String, Int>>(emptyMap())
    val progress: StateFlow<Map<String, Int>> = _progress

    fun selectDayType(dayType: DayType) {
        _selectedDayType.value = dayType
        _progress.value = emptyMap()
    }

    fun selectDifficulty(level: Int) {
        _difficulty.value = level.coerceIn(1, 5)
    }

    fun currentExercises(): List<ExerciseTemplate> =
        WorkoutPlans.plans[_selectedDayType.value].orEmpty()

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
        viewModelScope.launch {
            repository.logWorkout(
                date = LocalDate.now(),
                dayType = _selectedDayType.value,
                difficulty = _difficulty.value,
                completed = isWorkoutComplete()
            )
        }
    }
}
