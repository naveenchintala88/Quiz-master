package com.example.quizmaster.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quizmaster.viewmodel.QuizViewModel

@Composable
fun QuizScreen(navController: NavController, category: String, username: String) {
    val quizViewModel: QuizViewModel = viewModel()

    // Collect questions and loading state from ViewModel
    val questions by quizViewModel.questions.collectAsState(emptyList())
    val isLoading by quizViewModel.loading.collectAsState(false)

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf("") }

    // Fetch questions when the composable is first displayed
    LaunchedEffect(Unit) {
        quizViewModel.loadQuestions(category)
    }

    if (isLoading) {
        // Show loading spinner
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (questions.isNotEmpty()) {
        // Display the current question and options
        val currentQuestion = questions[currentQuestionIndex]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Category: $category", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(16.dp))

            Text("Question ${currentQuestionIndex + 1}: ${currentQuestion.question}", style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.height(16.dp))

            currentQuestion.options.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { selectedOption = option }
                ) {
                    RadioButton(
                        selected = selectedOption == option,
                        onClick = { selectedOption = option }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(option)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                if (currentQuestionIndex > 0) {
                    Button(onClick = { currentQuestionIndex-- }) {
                        Text("Previous")
                    }
                }
                if (currentQuestionIndex < questions.size - 1) {
                    Button(onClick = {
                        if (selectedOption == currentQuestion.correctAnswer) {
                            score++
                        }
                        currentQuestionIndex++
                        selectedOption = "" // Reset selected option
                    }) {
                        Text("Next")
                    }
                } else {
                    Button(onClick = {
                        if (selectedOption == currentQuestion.correctAnswer) {
                            score++
                        }
                        navController.navigate("result/$score/$username")
                    }) {
                        Text("Finish")
                    }
                }
            }
        }
    } else {
        // Display a message when no questions are available
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No questions available", style = MaterialTheme.typography.body1)
        }
    }
}
