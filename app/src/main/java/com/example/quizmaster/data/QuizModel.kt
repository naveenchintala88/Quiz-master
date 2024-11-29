package com.example.quizmaster.data

data class QuizResponse(
    val response_code: Int,
    val results: List<QuestionResponse>
)

data class QuestionResponse(
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)