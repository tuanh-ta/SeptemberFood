package com.septemberfood.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.septemberfood.controller.UserController
import com.septemberfood.databinding.ActivityMainBinding
import com.septemberfood.view.admin.AdminActivity
import com.septemberfood.view.customer.ProductListActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userController: UserController
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            
            userController = ViewModelProvider(this)[UserController::class.java]
            userController.initializeDefaultUsers()
            
            setupClickListeners()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Lỗi khởi tạo ứng dụng: ${e.message}", Toast.LENGTH_LONG).show()
            finish()
        }
    }
    
    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            lifecycleScope.launch {
                val user = userController.login(username, password)
                if (user != null) {
                    // Lưu thông tin user vào session
                    com.septemberfood.util.UserSession.saveUser(this@MainActivity, user)
                    
                    if (user.role == "Admin") {
                        startActivity(Intent(this@MainActivity, AdminActivity::class.java))
                    } else {
                        startActivity(Intent(this@MainActivity, ProductListActivity::class.java))
                    }
                    finish()
                } else {
                    Toast.makeText(this@MainActivity, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show()
                }
            }
        }
        
        binding.btnRegister.setOnClickListener {
            val dialog = RegisterDialogFragment()
            dialog.show(supportFragmentManager, "RegisterDialog")
        }
        
        binding.btnGuest.setOnClickListener {
            startActivity(Intent(this, ProductListActivity::class.java))
        }
    }
}

