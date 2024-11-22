package com.example.quizmaster.api

import com.example.quizmaster.data.QuizResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApi {
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") limit: Int = 10,
        @Query("category") category: String,
        @Query("difficulty") difficulty: String? = null,
        @Query("type") type: String? = null
    ): QuizResponse
}

