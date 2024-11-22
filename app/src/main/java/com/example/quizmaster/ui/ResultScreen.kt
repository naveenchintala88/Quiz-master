package com.example.quizmaster.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ResultScreen(navController: NavController, username: String, score: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Congratulations, $username!", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Your Score: $score/10", style = MaterialTheme.typography.h5)

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("home/$username") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Home")
        }
    }
}
