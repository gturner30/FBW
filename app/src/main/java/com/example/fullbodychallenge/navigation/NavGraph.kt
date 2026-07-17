package com.example.fullbodychallenge.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fullbodychallenge.ui.screens.ExerciseCounterScreen
import com.example.fullbodychallenge.ui.screens.HomeScreen
import com.example.fullbodychallenge.ui.screens.SummaryScreen
import com.example.fullbodychallenge.ui.screens.WorkoutListScreen
import com.example.fullbodychallenge.viewmodel.WorkoutViewModel

private object Routes {
    const val HOME = "home"
    const val WORKOUT_LIST = "workout_list"
    const val EXERCISE = "exercise/{exerciseId}"
    const val SUMMARY = "summary"
    fun exercise(id: String) = "exercise/$id"
}

@Composable
fun AppNavGraph(viewModel: WorkoutViewModel) {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                viewModel = viewModel,
                onStartWorkout = { navController.navigate(Routes.WORKOUT_LIST) }
            )
        }
        composable(Routes.WORKOUT_LIST) {
            WorkoutListScreen(
                viewModel = viewModel,
                onExerciseClick = { id -> navController.navigate(Routes.exercise(id)) },
                onFinish = { navController.navigate(Routes.SUMMARY) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.EXERCISE) { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getString("exerciseId") ?: return@composable
            ExerciseCounterScreen(
                viewModel = viewModel,
                exerciseId = exerciseId,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.SUMMARY) {
            SummaryScreen(
                viewModel = viewModel,
                onDone = {
                    navController.popBackStack(Routes.HOME, inclusive = false)
                }
            )
        }
    }
}
