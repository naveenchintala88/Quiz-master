package com.example.quizmaster.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    suspend fun getUser(username: String, password: String): User?

    @Query("SELECT * FROM User WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?

    @Query("UPDATE User SET password = :newPassword WHERE username = :username")
    suspend fun updatePassword(username: String, newPassword: String)
}