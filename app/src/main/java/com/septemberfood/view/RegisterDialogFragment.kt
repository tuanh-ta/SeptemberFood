package com.septemberfood.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.septemberfood.controller.UserController
import com.septemberfood.databinding.DialogRegisterBinding
import com.septemberfood.view.customer.ProductListActivity
import kotlinx.coroutines.launch

class RegisterDialogFragment : DialogFragment() {
    private var _binding: DialogRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var userController: UserController
    
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        userController = UserController(requireActivity().application)
        
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
        
        binding.btnCancelRegister.setOnClickListener {
            dismiss()
        }
    }
    
    private fun registerUser() {
        val name = binding.etRegisterName.text.toString().trim()
        val username = binding.etRegisterUsername.text.toString().trim()
        val password = binding.etRegisterPassword.text.toString().trim()
        val passwordConfirm = binding.etRegisterPasswordConfirm.text.toString().trim()
        val email = binding.etRegisterEmail.text.toString().trim()
        val phone = binding.etRegisterPhone.text.toString().trim()
        
        // Validation
        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
            Toast.makeText(context, "Vui lòng điền đầy đủ thông tin bắt buộc", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (password != passwordConfirm) {
            Toast.makeText(context, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show()
            return
        }
        
        lifecycleScope.launch {
            val result = userController.register(username, password, name, email, phone)
            
            when (result) {
                is UserController.RegisterResult.Success -> {
                    // Lưu thông tin user vào session
                    context?.let {
                        com.septemberfood.util.UserSession.saveUser(it, result.user)
                    }
                    Toast.makeText(context, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                    dismiss()
                    // Tự động đăng nhập sau khi đăng ký
                    startActivity(android.content.Intent(context, ProductListActivity::class.java))
                    activity?.finish()
                }
                is UserController.RegisterResult.Error -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

