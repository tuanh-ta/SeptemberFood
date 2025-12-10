package com.septemberfood.util

import android.content.Context
import android.content.SharedPreferences
import com.septemberfood.model.User

object UserSession {
    private const val PREFS_NAME = "user_session"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USERNAME = "username"
    private const val KEY_NAME = "name"
    private const val KEY_EMAIL = "email"
    private const val KEY_PHONE = "phone"
    private const val KEY_ROLE = "role"
    
    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    
    fun saveUser(context: Context, user: User) {
        val editor = getPrefs(context).edit()
        editor.putLong(KEY_USER_ID, user.id)
        editor.putString(KEY_USERNAME, user.username)
        editor.putString(KEY_NAME, user.name)
        editor.putString(KEY_EMAIL, user.email)
        editor.putString(KEY_PHONE, user.phone)
        editor.putString(KEY_ROLE, user.role)
        editor.apply()
    }
    
    fun getCurrentUser(context: Context): User? {
        val prefs = getPrefs(context)
        val userId = prefs.getLong(KEY_USER_ID, -1)
        if (userId == -1L) return null
        
        return User(
            id = userId,
            username = prefs.getString(KEY_USERNAME, "") ?: "",
            password = "", // Không lưu password
            name = prefs.getString(KEY_NAME, "") ?: "",
            email = prefs.getString(KEY_EMAIL, "") ?: "",
            phone = prefs.getString(KEY_PHONE, "") ?: "",
            role = prefs.getString(KEY_ROLE, "Customer") ?: "Customer"
        )
    }
    
    fun isLoggedIn(context: Context): Boolean {
        return getCurrentUser(context) != null
    }
    
    fun clearUser(context: Context) {
        val editor = getPrefs(context).edit()
        editor.clear()
        editor.apply()
    }
}

