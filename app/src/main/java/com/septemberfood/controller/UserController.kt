package com.septemberfood.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.septemberfood.database.AppDatabase
import com.septemberfood.database.UserDao
import com.septemberfood.model.User
import kotlinx.coroutines.launch

class UserController(application: Application) : AndroidViewModel(application) {
    private val userDao: UserDao = AppDatabase.getDatabase(application).userDao()
    
    suspend fun login(username: String, password: String): User? {
        return userDao.getUserByCredentials(username, password)
    }
    
    suspend fun register(
        username: String,
        password: String,
        name: String,
        email: String = "",
        phone: String = ""
    ): RegisterResult {
        // Kiểm tra username đã tồn tại chưa
        val existingUser = userDao.getUserByUsername(username)
        if (existingUser != null) {
            return RegisterResult.Error("Tên đăng nhập đã tồn tại")
        }
        
        // Kiểm tra validation
        if (username.isEmpty() || password.isEmpty() || name.isEmpty()) {
            return RegisterResult.Error("Vui lòng điền đầy đủ thông tin")
        }
        
        if (username.length < 3) {
            return RegisterResult.Error("Tên đăng nhập phải có ít nhất 3 ký tự")
        }
        
        if (password.length < 6) {
            return RegisterResult.Error("Mật khẩu phải có ít nhất 6 ký tự")
        }
        
        // Tạo user mới
        val newUser = User(
            username = username,
            password = password,
            role = "Customer",
            name = name,
            email = email,
            phone = phone
        )
        
        val userId = userDao.insertUser(newUser)
        return if (userId > 0) {
            RegisterResult.Success(newUser)
        } else {
            RegisterResult.Error("Đăng ký thất bại")
        }
    }
    
    sealed class RegisterResult {
        data class Success(val user: User) : RegisterResult()
        data class Error(val message: String) : RegisterResult()
    }
    
    fun initializeDefaultUsers() {
        viewModelScope.launch {
            // Check if default customer exists
            val customer = userDao.getUserByUsername("customer")
            if (customer == null) {
                userDao.insertUser(
                    User(
                        username = "customer",
                        password = "customer123",
                        role = "Customer",
                        name = "Customer"
                    )
                )
            }
        }
    }
}

