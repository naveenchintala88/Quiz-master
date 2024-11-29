package com.example.quizmaster.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quizmaster.R
import com.example.quizmaster.data.QuizDatabase
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(navController: NavController, database: QuizDatabase) {
    var username by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmationMessage by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFBBDEFB), Color(0xFF2196F3))
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
            Image(
                painter = painterResource(id = R.drawable.forgot_password_illustration),
                contentDescription = "Forgot Password Illustration",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Reset Your Password",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = "Username Icon")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("New Password") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = "Password Icon")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                if (username.isEmpty() || username.length < 3) {
                                    errorMessage = "Invalid username: Must be at least 3 characters long"
                                    confirmationMessage = ""
                                } else if (newPassword.isEmpty() || newPassword.length < 6) {
                                    errorMessage = "Password must be at least 6 characters long"
                                    confirmationMessage = ""
                                } else {
                                    val user = database.userDao().getUserByUsername(username)
                                    if (user != null) {
                                        database.userDao().updatePassword(username, newPassword)
                                        confirmationMessage = "Password updated successfully!"
                                        errorMessage = ""
                                        navController.navigate("login") {
                                            popUpTo("forgotPassword") { inclusive = true }
                                        }
                                    } else {
                                        errorMessage = "Username not found"
                                        confirmationMessage = ""
                                    }
                                }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Reset Password", fontSize = 18.sp)
                    }

                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.body2
                        )
                    }

                    if (confirmationMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = confirmationMessage,
                            color = MaterialTheme.colors.primary,
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { navController.navigate("login") }
            ) {
                Text("Back to Login", color = MaterialTheme.colors.onPrimary)
            }
        }
    }
}