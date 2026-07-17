package com.example.fullbodychallenge.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fullbodychallenge.ui.components.ExerciseAnimationView
import com.example.fullbodychallenge.viewmodel.WorkoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutListScreen(
    viewModel: WorkoutViewModel,
    onExerciseClick: (String) -> Unit,
    onFinish: () -> Unit,
    onBack: () -> Unit
) {
    val dayType by viewModel.todaysDayType.collectAsState()
    val difficulty by viewModel.difficulty.collectAsState()
    val progress by viewModel.progress.collectAsState()
    val exercises = viewModel.currentExercises()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(dayType?.displayName ?: "Workout") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Surface(tonalElevation = 4.dp) {
                Button(
                    onClick = { viewModel.finishWorkout(); onFinish() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(52.dp)
                ) {
                    Text(if (viewModel.isWorkoutComplete()) "Finish Workout" else "Finish Early")
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item { Spacer(Modifier.height(4.dp)) }
            items(exercises) { ex ->
                val target = ex.targetForLevel(difficulty) * ex.sets
                val done = progress[ex.id] ?: 0
                val complete = done >= target
                ListItem(
                    leadingContent = {
                        ExerciseAnimationView(
                            style = ex.animation,
                            modifier = Modifier.size(44.dp)
                        )
                    },
                    headlineContent = { Text(ex.nameForLevel(difficulty)) },
                    supportingContent = {
                        Text(
                            if (ex.isTimed) "$done / $target sec"
                            else "$done / $target reps · ${ex.sets} sets"
                        )
                    },
                    trailingContent = {
                        Icon(
                            if (complete) Icons.Filled.CheckCircle else Icons.Filled.RadioButtonUnchecked,
                            contentDescription = null,
                            tint = if (complete) MaterialTheme.colorScheme.primary
                                   else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    },
                    modifier = Modifier.clickable { onExerciseClick(ex.id) }
                )
                HorizontalDivider()
            }
            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}
