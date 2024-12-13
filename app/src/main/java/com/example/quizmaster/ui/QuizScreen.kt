package com.example.quizmaster.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4A148C),
                        Color(0xFF6A1B9A),
                        Color(0xFFAB47BC)
                    )
                )
            )
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.White, strokeWidth = 6.dp)
            }
        } else if (questions.isNotEmpty()) {
            val currentQuestion = questions[currentQuestionIndex]

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                LinearProgressIndicator(
                    progress = (currentQuestionIndex + 1) / questions.size.toFloat(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Color(0xFFAB47BC),
                    backgroundColor = Color(0xFF8E24AA)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Category: $category",
                    style = MaterialTheme.typography.h6.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                AnimatedVisibility(
                    visible = true,
                    enter = slideInHorizontally(initialOffsetX = { -300 }, animationSpec = tween(500))
                ) {
                    Text(
                        text = "Q${currentQuestionIndex + 1}: ${currentQuestion.question}",
                        style = MaterialTheme.typography.h6.copy(
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column {
                    currentQuestion.options.forEach { option ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    selectedOption = option
                                },
                            shape = RoundedCornerShape(16.dp),
                            backgroundColor = if (selectedOption == option) Color(0xFFAB47BC) else Color.White,
                            elevation = 6.dp
                        ) {
                            Text(
                                text = option,
                                style = MaterialTheme.typography.body1.copy(
                                    color = if (selectedOption == option) Color.White else Color.Black,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                ),
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (currentQuestionIndex > 0) {
                        FloatingActionButton(
                            onClick = { currentQuestionIndex-- },
                            backgroundColor = Color(0xFF6A1B9A),
                            contentColor = Color.White,
                            elevation = FloatingActionButtonDefaults.elevation(8.dp)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous")
                        }
                    }

                    if (currentQuestionIndex < questions.size - 1) {
                        FloatingActionButton(
                            onClick = {
                                if (selectedOption == currentQuestion.correctAnswer) {
                                    score++
                                }
                                currentQuestionIndex++
                                selectedOption = ""
                            },
                            backgroundColor = Color(0xFF6A1B9A),
                            contentColor = Color.White,
                            elevation = FloatingActionButtonDefaults.elevation(8.dp)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
                        }
                    } else {
                        FloatingActionButton(
                            onClick = {
                                if (selectedOption == currentQuestion.correctAnswer) {
                                    score++
                                }
                                navController.navigate("result/$score/$username")
                            },
                            backgroundColor = Color(0xFF6A1B9A),
                            contentColor = Color.White,
                            elevation = FloatingActionButtonDefaults.elevation(8.dp)
                        ) {
                            Text("Finish")
                        }
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No questions available",
                    style = MaterialTheme.typography.h6.copy(color = Color.White)
                )
            }
        }
    }
}