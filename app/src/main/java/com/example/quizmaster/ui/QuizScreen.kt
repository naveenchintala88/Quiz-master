package com.example.quizmaster.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quizmaster.viewmodel.QuizViewModel

@Composable
fun QuizScreen(navController: NavController, category: String, username: String) {
    val quizViewModel: QuizViewModel = viewModel()
    val questions by quizViewModel.questions.collectAsState(emptyList())
    val isLoading by quizViewModel.loading.collectAsState(false)

    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf("") }

    LaunchedEffect(category) {
        quizViewModel.loadQuestions(category)
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(modifier = Modifier.size(64.dp), color = MaterialTheme.colors.primary)
        }
    } else if (questions.isNotEmpty()) {
        val currentQuestion = questions[currentQuestionIndex]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF81D4FA),
                            Color(0xFF9575CD)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Text(
                text = "Category: $category",
                style = MaterialTheme.typography.h6.copy(fontSize = 22.sp),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Question ${currentQuestionIndex + 1}: ${currentQuestion.question}",
                style = MaterialTheme.typography.h6.copy(fontSize = 20.sp),
                modifier = Modifier.padding(bottom = 24.dp)
            )


            currentQuestion.options.forEach { option ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            selectedOption = option
                        },
                    shape = RoundedCornerShape(8.dp),
                    elevation = 4.dp,
                    backgroundColor = if (selectedOption == option) Color.LightGray else Color.White
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedOption == option,
                            onClick = { selectedOption = option }
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = option, style = MaterialTheme.typography.body1.copy(fontSize = 16.sp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))


            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                if (currentQuestionIndex > 0) {
                    Button(
                        onClick = { currentQuestionIndex-- },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Previous")
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))

                if (currentQuestionIndex < questions.size - 1) {
                    Button(
                        onClick = {
                            if (selectedOption == currentQuestion.correctAnswer) {
                                score++
                            }
                            currentQuestionIndex++
                            selectedOption = ""
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Next")
                    }
                } else {
                    Button(
                        onClick = {
                            if (selectedOption == currentQuestion.correctAnswer) {
                                score++
                            }
                            navController.navigate("result/$score/$username")
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Finish")
                    }
                }
            }
        }
    } else {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No questions available", style = MaterialTheme.typography.body1)
        }
    }
}