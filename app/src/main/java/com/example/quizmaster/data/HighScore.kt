package com.example.quizmaster.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HighScore(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String, // Player's name
    val score: Int,   // Player's score
    val date: Long    // Date of the score
)
