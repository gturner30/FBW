package com.example.fullbodychallenge.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fullbodychallenge.viewmodel.WorkoutViewModel

@Composable
fun SummaryScreen(
    viewModel: WorkoutViewModel,
    onDone: () -> Unit
) {
    val streak by viewModel.streak.collectAsState()
    val dayType by viewModel.todaysDayType.collectAsState()
    val complete = viewModel.isWorkoutComplete()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(if (complete) "🎉" else "💪", fontSize = 64.sp)
        Spacer(Modifier.height(16.dp))
        Text(
            if (complete) "${dayType?.displayName ?: "Workout"} complete!" else "Nice effort today",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        if (complete) {
            Text(
                "🔥 $streak day streak",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        } else {
            Text(
                "Come back and finish it later, or start fresh tomorrow.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        Spacer(Modifier.height(40.dp))
        Button(
            onClick = onDone,
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Back to Home")
        }
    }
}
