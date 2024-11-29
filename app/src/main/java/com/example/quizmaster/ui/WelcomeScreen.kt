package com.example.quizmaster.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quizmaster.R

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF3A3A7D),
                        Color(0xFF6A75A3)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo or Icon
            Image(
                painter = painterResource(id = R.drawable.quiz_logo),
                contentDescription = "Quiz Logo",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))


            Text(
                "Welcome to QuizMaster",
                style = MaterialTheme.typography.h4.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                ),
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))


            Button(
                onClick = { navController.navigate("signup") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF6F87D6)
                )
            ) {
                Text(
                    "Sign Up",
                    style = MaterialTheme.typography.button.copy(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            OutlinedButton(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF6F87D6)
                )
            ) {
                Text(
                    "Login",
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