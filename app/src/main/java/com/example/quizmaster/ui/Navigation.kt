package com.example.quizmaster.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.quizmaster.ui.CategoryScreen
import com.example.quizmaster.ui.QuizScreen
import com.example.quizmaster.ui.ResultScreen
import com.example.quizmaster.viewmodel.QuizViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "category_screen",
        modifier = modifier
    ) {
        // Category Screen
        composable("category_screen") {
            CategoryScreen(onCategorySelected = { categoryId ->
                navController.navigate("quiz_screen/$categoryId")
            })
        }

        // Quiz Screen
        composable(
            route = "quiz_screen/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
            QuizScreen(
                viewModel = viewModel,
                category = categoryId,
                onFinish = {
                    navController.navigate("result_screen")
                }
            )
        }

        // Result Screen
        composable("result_screen") {
            ResultScreen(viewModel = viewModel, onRestart = {
                navController.popBackStack("category_screen", inclusive = false)
            })
        }
    }
}
