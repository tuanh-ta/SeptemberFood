package com.septemberfood.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.septemberfood.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun getUserByCredentials(username: String, password: String): User?
    
    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long
}

