package com.example.quizmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizmaster.data.QuizRepository
import com.example.quizmaster.model.QuestionResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    // StateFlow to hold the list of questions
    private val _questions = MutableStateFlow<List<QuestionResponse>>(emptyList())
    val questions: StateFlow<List<QuestionResponse>> get() = _questions

    // StateFlow to manage loading state
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    fun loadQuestions(category: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val fetchedQuestions = QuizRepository.fetchQuestions(category)
                _questions.value = fetchedQuestions
            } catch (e: Exception) {
                // In case of an error, set an empty list
                _questions.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }
}
