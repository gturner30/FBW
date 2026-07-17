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
import com.example.fullbodychallenge.viewmodel.WorkoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: WorkoutViewModel,
    onStartWorkout: () -> Unit,
    onManageRestDays: () -> Unit
) {
    val streak by viewModel.streak.collectAsState()
    val todaysDayType by viewModel.todaysDayType.collectAsState()
    val difficulty by viewModel.difficulty.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))
        Text("Full Body Challenge", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(8.dp))
        Text(
            if (streak > 0) "🔥 $streak day streak" else "Let's start a streak today",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(Modifier.height(40.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (todaysDayType != null) {
                    Text("Today's Focus", style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                    Spacer(Modifier.height(4.dp))
                    Text(todaysDayType!!.displayName, style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold)
                } else {
                    Text("😌", style = MaterialTheme.typography.headlineLarge)
                    Spacer(Modifier.height(4.dp))
                    Text("Rest Day", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Recovery is part of the program too.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }

        TextButton(onClick = onManageRestDays, modifier = Modifier.padding(top = 8.dp)) {
            Text("Manage rest days")
        }

        Spacer(Modifier.height(28.dp))
        Text("Difficulty", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(4.dp))
        Text(
            "Level $difficulty of 5",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            (1..5).forEach { level ->
                DifficultyDot(
                    level = level,
                    selected = level == difficulty,
                    onClick = { viewModel.selectDifficulty(level) }
                )
            }
        }

        Spacer(Modifier.weight(1f))

        if (todaysDayType != null) {
            Button(
                onClick = onStartWorkout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Start Workout", style = MaterialTheme.typography.titleLarge)
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun DifficultyDot(level: Int, selected: Boolean, onClick: () -> Unit) {
    val colors = if (selected) {
        ButtonDefaults.buttonColors()
    } else {
        ButtonDefaults.outlinedButtonColors()
    }
    Button(
        onClick = onClick,
        colors = colors,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.size(48.dp)
    ) {
        Text("$level", fontWeight = FontWeight.Bold)
    }
}
