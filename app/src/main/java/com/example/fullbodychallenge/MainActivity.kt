package com.example.fullbodychallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fullbodychallenge.data.AppDatabase
import com.example.fullbodychallenge.data.WorkoutRepository
import com.example.fullbodychallenge.navigation.AppNavGraph
import com.example.fullbodychallenge.ui.theme.FullBodyChallengeTheme
import com.example.fullbodychallenge.viewmodel.WorkoutViewModel
import com.example.fullbodychallenge.viewmodel.WorkoutViewModelFactory
import androidx.compose.material3.MaterialTheme

class MainActivity : ComponentActivity() {

    private val repository by lazy {
        WorkoutRepository(AppDatabase.getInstance(applicationContext).workoutDao())
    }

    private val viewModel: WorkoutViewModel by viewModels {
        WorkoutViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullBodyApp(viewModel)
        }
    }
}

@Composable
private fun FullBodyApp(viewModel: WorkoutViewModel) {
    FullBodyChallengeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppNavGraph(viewModel = viewModel)
        }
    }
}
