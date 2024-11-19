package com.example.quizmaster.data

import com.example.quizmaster.data.remote.ApiClient

class QuizRepository(private val apiService: QuizApiService = ApiClient.apiService) {
    fun getQuizQuestions(amount: Int, category: Int, difficulty: String) =
        apiService.getQuizQuestions(amount, category, difficulty)
}