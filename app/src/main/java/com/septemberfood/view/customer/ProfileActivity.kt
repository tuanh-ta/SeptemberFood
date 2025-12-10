package com.septemberfood.view.customer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.septemberfood.controller.UserController
import com.septemberfood.databinding.ActivityProfileBinding
import com.septemberfood.util.UserSession
import com.septemberfood.view.MainActivity
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var userController: UserController
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        userController = UserController(application)
        
        setupToolbar()
        loadUserInfo()
        
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }
    
    private fun setupToolbar() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
    
    private fun loadUserInfo() {
        val user = UserSession.getCurrentUser(this)
        if (user != null) {
            binding.tvUsername.text = user.username
            binding.tvName.text = user.name.ifEmpty { "Chưa cập nhật" }
            binding.tvEmail.text = user.email.ifEmpty { "Chưa cập nhật" }
            binding.tvPhone.text = user.phone.ifEmpty { "Chưa cập nhật" }
            binding.tvRole.text = "Khách hàng"
        } else {
            Toast.makeText(this, "Chưa đăng nhập", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    
    private fun logout() {
        UserSession.clearUser(this)
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}

