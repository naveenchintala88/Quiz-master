package com.example.quizmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.quizmaster.data.QuizDatabase
import com.example.quizmaster.ui.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = QuizDatabase.getDatabase(this)

        setContent {
            AppNavigation(database)
        }
    }
}