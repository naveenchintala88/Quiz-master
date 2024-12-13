package com.example.quizmaster.ui

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quizmaster.data.Category
import com.example.quizmaster.ui.screens.LoginActivity
import com.example.quizmaster.viewmodel.QuizViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val quizViewModel: QuizViewModel = viewModel()
    val categories by quizViewModel.categories.collectAsState(emptyList())
    val isLoading by quizViewModel.loading.collectAsState(false)
    var username by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var isMenuExpanded by remember { mutableStateOf(false) }
    var isEditDialogVisible by remember { mutableStateOf(false) }
    var newUsername by remember { mutableStateOf("") }

    val alphaWelcomeText by animateFloatAsState(targetValue = if (categories.isNotEmpty()) 1f else 0.7f,
        label = ""
    )

    LaunchedEffect(Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        username = user?.displayName ?: "Guest"
        coroutineScope.launch {
            quizViewModel.loadCategories()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF6A1B9A), Color(0xFF4A148C))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Quiz Master",
                    style = MaterialTheme.typography.h4.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    )
                )
                IconButton(
                    onClick = { isMenuExpanded = true },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                }

                DropdownMenu(
                    expanded = isMenuExpanded,
                    onDismissRequest = { isMenuExpanded = false }
                ) {
                    DropdownMenuItem(onClick = {
                        isEditDialogVisible = true
                        isMenuExpanded = false
                    }) {
                        Text("Edit Profile")
                    }
                    DropdownMenuItem(onClick = {
                        FirebaseAuth.getInstance().signOut()
                        val intent = Intent(navController.context, LoginActivity::class.java)
                        navController.context.startActivity(intent)
                    }) {
                        Text("Logout")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Welcome, $username!",
                style = MaterialTheme.typography.h5.copy(color = Color.White, fontWeight = FontWeight.SemiBold),
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(alphaWelcomeText)
                    .padding(start = 7.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Select a category to start the quiz:",
                style = MaterialTheme.typography.h6.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 7.dp)
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
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(categories) { category ->
                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn()
                        ) {
                            CategoryCard(category = category, navController = navController, username = username)
                        }
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

        FloatingActionButton(
            onClick = { isEditDialogVisible = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            backgroundColor = Color(0xFF4A148C)
        ) {
            Icon(Icons.Default.Person, contentDescription = "Edit Profile", tint = Color.White)
        }

        if (isEditDialogVisible) {
            Dialog(onDismissRequest = { isEditDialogVisible = false }) {
                Surface(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    shape = MaterialTheme.shapes.medium,
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Edit Profile",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        TextField(
                            value = newUsername,
                            onValueChange = { newUsername = it },
                            label = { Text("New Username") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = { isEditDialogVisible = false }) {
                                Text("Cancel")
                            }
                            Button(onClick = {
                                if (newUsername.isNotBlank()) {
                                    updateUsername(newUsername) { success ->
                                        if (success) {
                                            username = newUsername
                                            isEditDialogVisible = false
                                        }
                                    }
                                }
                            }) {
                                Text("Save")
                            }
                        }
                    }
                }
            }
        }
    }
}

fun updateUsername(newUsername: String, onComplete: (Boolean) -> Unit) {
    val user = FirebaseAuth.getInstance().currentUser
    if (user != null) {
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(newUsername)
            .build()

        user.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    } else {
        onComplete(false)
    }
}

@Composable
fun CategoryCard(category: Category, navController: NavController, username: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("quiz/${category.id}/$username")
            },
        elevation = 12.dp,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color(0xFF9A66CC)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.h6.copy(color = Color.White, fontWeight = FontWeight.Bold)
            )
        }
    }
}