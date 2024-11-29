package com.example.quizmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizmaster.data.QuizRepository
import com.example.quizmaster.data.Category
import com.example.quizmaster.model.QuestionResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    private val _questions = MutableStateFlow<List<QuestionResponse>>(emptyList())
    val questions: StateFlow<List<QuestionResponse>> get() = _questions

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    fun loadQuestions(category: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val fetchedQuestions = QuizRepository.fetchQuestions(category)
                _questions.value = fetchedQuestions
            } catch (e: Exception) {
                _questions.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            try {
                _loading.value = true
                val fetchedCategories = QuizRepository.fetchCategories()
                _categories.value = fetchedCategories
            } catch (e: Exception) {
                _categories.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }
}