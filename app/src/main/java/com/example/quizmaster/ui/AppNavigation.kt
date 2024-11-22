package com.example.quizmaster.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizmaster.data.QuizDatabase

@Composable
fun AppNavigation(database: QuizDatabase) {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "signup") {
        composable("signup") { SignUpScreen(navController, database) }
        composable("login") { LoginScreen(navController, database) }
        composable("home/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Guest"
            HomeScreen(navController, username)
        }
        composable("quiz/{category}/{username}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: "General"
            val username = backStackEntry.arguments?.getString("username") ?: "Guest"
            QuizScreen(navController, category, username)
        }
        composable("result/{score}/{username}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toInt() ?: 0
            val username = backStackEntry.arguments?.getString("username") ?: "Guest"
            ResultScreen(navController, username, score)
        }
    }
}
