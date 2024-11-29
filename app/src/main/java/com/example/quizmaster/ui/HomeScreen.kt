package com.example.quizmaster.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quizmaster.data.Category
import com.example.quizmaster.viewmodel.QuizViewModel

@Composable
fun HomeScreen(navController: NavController, username: String) {
    val quizViewModel: QuizViewModel = viewModel()
    val categories by quizViewModel.categories.collectAsState(emptyList())
    val isLoading by quizViewModel.loading.collectAsState(false)

    LaunchedEffect(Unit) {
        quizViewModel.loadCategories()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(Color(0xFF6200EE), Color(0xFF3700B3))))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Welcome, $username!",
                style = MaterialTheme.typography.h4.copy(color = Color.White),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Select a category to start the quiz:",
                style = MaterialTheme.typography.h6.copy(color = Color.White),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 4.dp
                    )
                }
            } else if (categories.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(categories) { category ->
                        CategoryCard(category = category, navController = navController, username = username)
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No categories available", style = MaterialTheme.typography.body1.copy(color = Color.White))
                }
            }
        }
    }
}

@Composable
fun CategoryCard(category: Category, navController: NavController, username: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("quiz/${category.id}/$username")
            },
        shape = RoundedCornerShape(16.dp),
        elevation = 12.dp,
        backgroundColor = Color(0xFF9A66CC)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = category.name,
                    style = MaterialTheme.typography.h6.copy(color = Color.White),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}