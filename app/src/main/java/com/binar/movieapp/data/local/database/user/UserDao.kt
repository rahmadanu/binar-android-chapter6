package com.binar.movieapp.data.local.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.binar.movieapp.data.model.user.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun registerUser(user: UserEntity): Long

    @Query("SELECT * FROM user_information WHERE username = :username")
    suspend fun getUser(username: String) : UserEntity
}