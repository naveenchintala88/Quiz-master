package com.example.quizmaster.data

import com.example.quizmaster.api.RetrofitInstance
import com.example.quizmaster.model.QuestionResponse

object QuizRepository {
    suspend fun fetchQuestions(categoryId: String): List<QuestionResponse> {
        val quizResponse = RetrofitInstance.api.getQuestions(category = categoryId)
        return quizResponse.results.map { result ->
            QuestionResponse(
                question = result.question,
                options = (result.incorrect_answers + result.correct_answer).shuffled(),
                correctAnswer = result.correct_answer
            )
        }
    }

    suspend fun fetchCategories(): List<Category> {
        val categoryResponse = RetrofitInstance.api.getCategories()
        return categoryResponse.trivia_categories
    }
}