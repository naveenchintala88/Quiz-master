package com.example.quizmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.quizmaster.data.QuizRepository
import com.example.quizmaster.ui.navigation.NavigationGraph
import com.example.quizmaster.ui.theme.QuizMasterTheme
import com.example.quizmaster.viewmodel.QuizViewModel
import com.example.quizmaster.viewmodel.QuizViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizMasterTheme {
                val navController = rememberNavController()

                // Initialize the repository
                val repository = QuizRepository() // Ensure this is correctly initialized

                // Create the ViewModelFactory
                val factory = QuizViewModelFactory(repository)

                // Use the ViewModelProvider with the factory to get the QuizViewModel instance
                val viewModel: QuizViewModel = ViewModelProvider(this, factory).get(QuizViewModel::class.java)

                NavigationGraph(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}