package com.septemberfood.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val username: String,
    val password: String,
    val role: String = "Customer",
    val name: String = "",
    val email: String = "",
    val phone: String = ""
)

