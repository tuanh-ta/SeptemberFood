package com.septemberfood.view.customer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.septemberfood.controller.CartController
import com.septemberfood.databinding.ActivityCartBinding
import com.septemberfood.model.CartItem
import com.septemberfood.model.Product
import com.septemberfood.view.adapter.CartAdapter
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var cartController: CartController
    private lateinit var cartAdapter: CartAdapter
    private var cartItemsWithProducts: List<Pair<CartItem, Product>> = emptyList()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // Ẩn ActionBar
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        cartController = ViewModelProvider(this)[CartController::class.java]
        
        setupRecyclerView()
        observeCartItems()
        
        binding.btnCheckout.setOnClickListener {
            if (cartItemsWithProducts.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng trống", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, CheckoutActivity::class.java))
            }
        }
        
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
    
    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            onQuantityChange = { cartItem, newQuantity ->
                cartController.updateCartItemQuantity(cartItem, newQuantity)
            },
            onRemove = { cartItem ->
                cartController.removeFromCart(cartItem)
            }
        )
        
        binding.recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }
    }
    
    private fun observeCartItems() {
        cartController.allCartItems.observe(this) { cartItems ->
            lifecycleScope.launch {
                cartItemsWithProducts = cartController.getCartItemsWithProducts(cartItems)
                cartAdapter.submitList(cartItemsWithProducts)
                val total = cartController.getCartTotal(cartItems)
                binding.tvTotal.text = "Tổng tiền: ${total.toInt()} VNĐ"
                
                if (cartItemsWithProducts.isEmpty()) {
                    binding.tvEmpty.visibility = View.VISIBLE
                    binding.btnCheckout.isEnabled = false
                } else {
                    binding.tvEmpty.visibility = View.GONE
                    binding.btnCheckout.isEnabled = true
                }
            }
        }
    }
}

