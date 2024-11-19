package com.example.quizmaster.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizmaster.data.QuizRepository
import com.example.quizmaster.data.QuizResponse
import com.example.quizmaster.data.remote.QuizQuestion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuizViewModel(private val repository: QuizRepository) : ViewModel() {

    // LiveData to hold the list of quiz questions
    private val _quizQuestions = MutableLiveData<List<QuizQuestion>>()
    val quizQuestions: LiveData<List<QuizQuestion>> = _quizQuestions

    // LiveData to hold the selected answers for each question
    private val _selectedAnswers = MutableLiveData<Map<Int, String>>(emptyMap())
    val selectedAnswers: LiveData<Map<Int, String>> = _selectedAnswers

    // Fetch quiz questions from the repository
    fun fetchQuizQuestions(amount: Int, category: Int, difficulty: String) {
        repository.getQuizQuestions(amount, category, difficulty).enqueue(object : Callback<QuizResponse> {
            override fun onResponse(call: Call<QuizResponse>, response: Response<QuizResponse>) {
                if (response.isSuccessful) {
                    _quizQuestions.value = (response.body()?.results ?: emptyList()) as List<QuizQuestion>?
                } else {
                    println("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<QuizResponse>, t: Throwable) {
                println("Error fetching quiz questions: ${t.message}")
            }
        })
    }

    // Method to select an answer for a specific question
    fun selectAnswer(questionIndex: Int, answer: String) {
        // Get the current map of selected answers
        val currentAnswers = _selectedAnswers.value ?: emptyMap()
        // Update the selected answer for the current question index
        val updatedAnswers = currentAnswers.toMutableMap().apply {
            this[questionIndex] = answer
        }
        // Update the LiveData with the new map
        _selectedAnswers.value = updatedAnswers
    }
}