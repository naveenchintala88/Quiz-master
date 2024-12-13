package com.example.quizmaster.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quizmaster.R
import kotlinx.coroutines.delay

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun WelcomeScreen(navController: NavController) {
    var isVisible by remember { mutableStateOf(false) }
    var buttonOffset by remember { mutableFloatStateOf(100f) }

    LaunchedEffect(Unit) {
        delay(300)
        isVisible = true
        delay(200)
        buttonOffset = 0f
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF4A148C),
                        Color(0xFF6A1B9A),
                        Color(0xFF8E24AA)
                    ),
                    center = Offset(400f, 800f),
                    radius = 800f
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn()
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF6A1B9A),
                        modifier = Modifier
                            .size(180.dp)
                            .graphicsLayer {
                                shadowElevation = 10.dp.toPx()
                            }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.quiz_logo),
                            contentDescription = "Quiz Logo",
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Welcome to",
                            style = TextStyle(
                                color = Color.White,
                                fontWeight = FontWeight.Normal,
                                fontSize = 24.sp
                            )
                        )
                        Text(
                            "QuizMaster",
                            style = MaterialTheme.typography.h4.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 36.sp
                            )
                        )
                        Text(
                            "Challenge your knowledge!",
                            style = TextStyle(
                                color = Color(0xFFDDDDFF),
                                fontWeight = FontWeight.Light,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            Button(
                onClick = { navController.navigate("home/Guest") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .offset(y = animateDpAsState(targetValue = buttonOffset.dp - 50.dp, label = "").value),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFE040FB)
                )
            ) {
                Text(
                    "Start Quiz",
                    style = MaterialTheme.typography.button.copy(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

        }
    }
}