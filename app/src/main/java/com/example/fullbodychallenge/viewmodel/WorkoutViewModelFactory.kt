package com.example.fullbodychallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fullbodychallenge.data.RestDayPreferences
import com.example.fullbodychallenge.data.WorkoutRepository

class WorkoutViewModelFactory(
    private val repository: WorkoutRepository,
    private val restDayPreferences: RestDayPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return WorkoutViewModel(repository, restDayPreferences) as T
    }
}
