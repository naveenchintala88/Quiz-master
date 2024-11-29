package com.example.quizmaster.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HighScoreDao {
    @Insert
    suspend fun insertHighScore(highScore: HighScore)

    @Query("SELECT * FROM HighScore ORDER BY score DESC LIMIT 10")
    fun getTopScores(): Flow<List<HighScore>>
}