package com.example.fullbodychallenge.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fullbodychallenge.data.ExerciseTemplate
import com.example.fullbodychallenge.ui.components.CircularRepDial
import com.example.fullbodychallenge.ui.components.CircularTimerRing
import com.example.fullbodychallenge.ui.components.ExerciseAnimationView
import com.example.fullbodychallenge.viewmodel.WorkoutViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseCounterScreen(
    viewModel: WorkoutViewModel,
    exerciseId: String,
    onBack: () -> Unit
) {
    val difficulty by viewModel.difficulty.collectAsState()
    val progressMap by viewModel.progress.collectAsState()
    val exercise = viewModel.currentExercises().first { it.id == exerciseId }
    val targetTotal = exercise.targetForLevel(difficulty) * exercise.sets
    val loggedTotal = progressMap[exercise.id] ?: 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(exercise.nameForLevel(difficulty)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ExerciseAnimationView(
                style = exercise.animation,
                modifier = Modifier.size(96.dp)
            )
            Spacer(Modifier.height(12.dp))

            exercise.equipmentNote?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            Spacer(Modifier.weight(1f))

            if (exercise.isTimed) {
                TimedDial(
                    exercise = exercise,
                    targetTotal = targetTotal,
                    loggedTotal = loggedTotal,
                    onLog = { viewModel.logProgress(exercise.id, it) }
                )
            } else {
                RepDial(
                    exercise = exercise,
                    targetTotal = targetTotal,
                    loggedTotal = loggedTotal,
                    onLog = { viewModel.logProgress(exercise.id, it) }
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                "${exercise.sets} sets · ${exercise.targetForLevel(difficulty)} " +
                    (if (exercise.isTimed) "sec each" else "reps each"),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                Text("Done")
            }
        }
    }
}

@Composable
private fun RepDial(
    exercise: ExerciseTemplate,
    targetTotal: Int,
    loggedTotal: Int,
    onLog: (Int) -> Unit
) {
    val perLap = (targetTotal / exercise.sets).coerceAtLeast(1)
    var accumulatedDegrees by remember(exercise.id) { mutableFloatStateOf(loggedTotal.toFloat() / perLap * 360f) }
    val degreesPerRep = 360f / perLap

    val currentReps = (accumulatedDegrees / degreesPerRep).toInt().coerceIn(0, targetTotal)
    val lapProgress = ((accumulatedDegrees % 360f) / 360f).let { if (it < 0) it + 1f else it }

    CircularRepDial(
        progressFraction = if (currentReps >= targetTotal) 1f else lapProgress,
        ringColor = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
        onAngleDelta = { delta ->
            val newDegrees = (accumulatedDegrees + delta).coerceIn(0f, degreesPerRep * targetTotal)
            accumulatedDegrees = newDegrees
            onLog((newDegrees / degreesPerRep).toInt().coerceIn(0, targetTotal))
        },
        modifier = Modifier.fillMaxWidth(0.75f)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("$currentReps", style = MaterialTheme.typography.headlineLarge)
            Text("/ $targetTotal reps", style = MaterialTheme.typography.bodyMedium)
            Text("Spin to count", style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
        }
    }
}

@Composable
private fun TimedDial(
    exercise: ExerciseTemplate,
    targetTotal: Int,
    loggedTotal: Int,
    onLog: (Int) -> Unit
) {
    var elapsed by remember(exercise.id) { mutableIntStateOf(loggedTotal) }
    var running by remember { mutableStateOf(false) }

    LaunchedEffect(running) {
        while (running && elapsed < targetTotal) {
            delay(1000)
            elapsed += 1
            onLog(elapsed)
        }
        if (elapsed >= targetTotal) running = false
    }

    Box(contentAlignment = Alignment.Center) {
        CircularTimerRing(
            progressFraction = if (targetTotal == 0) 0f else elapsed.toFloat() / targetTotal,
            ringColor = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.fillMaxWidth(0.75f)
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("${elapsed}s", style = MaterialTheme.typography.headlineLarge)
            Text("/ ${targetTotal}s total", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(12.dp))
            IconButton(onClick = { running = !running }) {
                Icon(
                    if (running) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (running) "Pause" else "Start",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}
