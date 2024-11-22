package com.example.quizmaster.model

data class QuestionResponse(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)
