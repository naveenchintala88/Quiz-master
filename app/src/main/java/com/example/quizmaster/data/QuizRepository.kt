package com.example.quizmaster.data

import com.example.quizmaster.api.RetrofitInstance
import com.example.quizmaster.model.QuestionResponse
import kotlin.collections.map

object QuizRepository {
    suspend fun fetchQuestions(categoryId: String): List<QuestionResponse> {
        val quizResponse = RetrofitInstance.api.getQuestions(category = categoryId)

        // Ensure `results` is iterable
        return quizResponse.results.map { result ->
            QuestionResponse(
                question = result.question,
                options = (result.incorrect_answers + result.correct_answer).shuffled(),
                correctAnswer = result.correct_answer
            )
        }
    }
}

