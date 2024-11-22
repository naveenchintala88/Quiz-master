package com.example.quizmaster.data

import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface QuizApiService {
    @GET("api_category.php")
    suspend fun getCategories(): QuizResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://opentdb.com/"

    val api: QuizApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizApiService::class.java)
    }
}
