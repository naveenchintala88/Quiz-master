package com.example.quizmaster.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController, username: String) {
    val categories = listOf("Science", "Math", "History", "General Knowledge")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Welcome, $username!", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Select a category to start the quiz:", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(16.dp))
        for (category in categories) {
            Text(
                text = category,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { navController.navigate("quiz/$category/$username") },
                style = MaterialTheme.typography.body1
            )
        }
    }
}
