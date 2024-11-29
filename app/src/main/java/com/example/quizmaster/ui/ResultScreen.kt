package com.example.quizmaster.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ResultScreen(navController: NavController, username: String, score: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF3F51B5),
                        Color(0xFF9C27B0)
                    )
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Congratulations, $username!",
            style = MaterialTheme.typography.h4.copy(
                color = Color.White,
                fontSize = 30.sp,
                letterSpacing = 1.5.sp
            )
        )

        Spacer(modifier = Modifier.height(24.dp))


        Text(
            text = "Your Score: $score/10",
            style = MaterialTheme.typography.h5.copy(
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(32.dp))


        Button(
            onClick = { navController.navigate("home/$username") },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9C27B0))
        ) {
            Text("Go Back to Home", style = MaterialTheme.typography.button.copy(color = Color.White))
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    ResultScreen(navController = NavController(context = LocalContext.current), username = "John", score = 8)
}