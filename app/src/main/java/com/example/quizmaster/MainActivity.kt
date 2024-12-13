package com.example.quizmaster

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizmaster.ui.HomeScreen
import com.example.quizmaster.ui.QuizScreen
import com.example.quizmaster.ui.ResultScreen
import com.example.quizmaster.ui.WelcomeScreen
import com.example.quizmaster.ui.screens.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            setContent {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") {
                        WelcomeScreen(navController)
                    }
                    composable("home/{username}") { backStackEntry ->
                        backStackEntry.arguments?.getString("username") ?: "Guest"
                        HomeScreen(navController)
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
        }
    }
}