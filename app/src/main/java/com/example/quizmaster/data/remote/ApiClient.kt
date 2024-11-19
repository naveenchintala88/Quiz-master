package com.example.quizmaster.data.remote

import com.example.quizmaster.data.QuizApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiClient {

    private const val BASE_URL = "https://opentdb.com/"

    // Retrofit instance
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Base URL of the Open Trivia API
            .addConverterFactory(GsonConverterFactory.create()) // Add GsonConverterFactory for JSON parsing
            .build()
    }

    // API Service instance
    val apiService: QuizApiService by lazy {
        retrofit.create(QuizApiService::class.java)
    }
}