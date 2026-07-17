package com.example.fullbodychallenge.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fullbodychallenge.viewmodel.WorkoutViewModel

private val weekdays = listOf(
    1 to "Monday", 2 to "Tuesday", 3 to "Wednesday", 4 to "Thursday",
    5 to "Friday", 6 to "Saturday", 7 to "Sunday"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestDaysScreen(
    viewModel: WorkoutViewModel,
    onBack: () -> Unit
) {
    val restDays by viewModel.restDays.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rest Days") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(24.dp)) {
            Text(
                "Choose up to 2 days a week to rest. The workout rotation " +
                    "automatically skips these and keeps flowing on the days you train.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(Modifier.height(24.dp))
            weekdays.forEach { (iso, name) ->
                val selected = iso in restDays
                ListItem(
                    headlineContent = { Text(name) },
                    trailingContent = {
                        Switch(
                            checked = selected,
                            onCheckedChange = { viewModel.toggleRestDay(iso) },
                            enabled = selected || restDays.size < 2
                        )
                    }
                )
                HorizontalDivider()
            }
            Spacer(Modifier.height(12.dp))
            Text(
                "${restDays.size} of 2 rest days selected",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
