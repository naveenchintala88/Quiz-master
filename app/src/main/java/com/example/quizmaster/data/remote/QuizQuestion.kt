package com.example.quizmaster.data.remote

data class QuizQuestion(
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val options: List<String>
)