package com.example.quizmaster.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizmaster.viewmodel.QuizViewModel

@Composable
fun QuizScreen(viewModel: QuizViewModel = viewModel(), category: Int, onFinish: () -> Unit) {
    // Observe quiz questions and selected answers from the ViewModel
    val quizQuestions by viewModel.quizQuestions.observeAsState(emptyList())
    val selectedAnswers by viewModel.selectedAnswers.observeAsState(emptyMap())
    var currentIndex by remember { mutableStateOf(0) }

    // Check if there are questions to display
    if (quizQuestions.isNotEmpty()) {
        val question = quizQuestions[currentIndex]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Question ${currentIndex + 1}/${quizQuestions.size}",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = question.question, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))

            // Display Options
            question.options.forEach { option ->
                val isSelected = selectedAnswers[currentIndex] == option
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { viewModel.selectAnswer(currentIndex, option) }
                        .background(if (isSelected) Color.Green else MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = option,
                        modifier = Modifier.padding(16.dp),
                        color = if (isSelected) Color.White else Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Navigation Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (currentIndex > 0) {
                    Button(onClick = { currentIndex-- }) {
                        Text("Previous")
                    }
                }

                if (currentIndex < quizQuestions.size - 1) {
                    Button(onClick = { currentIndex++ }) {
                        Text("Next")
                    }
                } else {
                    Button(onClick = onFinish) {
                        Text("Finish")
                    }
                }
            }
        }
    } else {
        // Optionally, display a loading indicator or a message if there are no questions
        Text(text = "Loading questions...", style = MaterialTheme.typography.bodyLarge)
    }
}