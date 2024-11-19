package com.example.quizmaster.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quizmaster.viewmodel.QuizViewModel

@Composable
fun ResultScreen(viewModel: QuizViewModel, onRestart: () -> Unit) {
    val quizQuestions = viewModel.quizQuestions.value ?: emptyList()
    val selectedAnswers = viewModel.selectedAnswers.value ?: emptyMap()

    // Count correct answers using forEachIndexed
    var correctAnswers = 0
    quizQuestions.forEachIndexed { index, question ->
        if (selectedAnswers[index] == question.correctAnswer) {
            correctAnswers++
        }
    }

    val percentage = if (quizQuestions.isNotEmpty()) {
        (correctAnswers.toDouble() / quizQuestions.size) * 100
    } else {
        0.0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Quiz Completed!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Correct Answers: $correctAnswers/${quizQuestions.size}")
        Text("Score: ${"%.2f".format(percentage)}%", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRestart) {
            Text("Restart")
        }
    }
}