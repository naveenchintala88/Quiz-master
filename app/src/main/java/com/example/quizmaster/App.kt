package com.example.quizmaster

import android.app.Application
import com.example.quizmaster.data.QuizDatabase

class App : Application() {
    val database by lazy { QuizDatabase.getDatabase(this) }
}
