package com.septemberfood.view.customer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.septemberfood.controller.CartController
import com.septemberfood.controller.OrderController
import com.septemberfood.databinding.ActivityCheckoutBinding
import kotlinx.coroutines.launch

class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var cartController: CartController
    private lateinit var orderController: OrderController
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // Ẩn ActionBar
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        cartController = ViewModelProvider(this)[CartController::class.java]
        orderController = ViewModelProvider(this)[OrderController::class.java]
        
        loadCartTotal()
        
        binding.btnPlaceOrder.setOnClickListener {
            val name = binding.etCustomerName.text.toString().trim()
            val phone = binding.etCustomerPhone.text.toString().trim()
            val address = binding.etCustomerAddress.text.toString().trim()
            
            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            val cartItems = cartController.allCartItems.value ?: emptyList()
            orderController.createOrder(name, phone, address, cartItems) { orderId ->
                if (orderId > 0) {
                    Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }
        
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
    
    private fun loadCartTotal() {
        cartController.allCartItems.observe(this) { cartItems ->
            lifecycleScope.launch {
                val total = cartController.getCartTotal(cartItems)
                binding.tvTotalAmount.text = "${total.toInt()} VNĐ"
            }
        }
    }
}

