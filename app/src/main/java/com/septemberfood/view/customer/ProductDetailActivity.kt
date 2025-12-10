package com.septemberfood.view.customer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.septemberfood.controller.CartController
import com.septemberfood.controller.ProductController
import com.septemberfood.databinding.ActivityProductDetailBinding
import com.septemberfood.util.ImageLoader
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var productController: ProductController
    private lateinit var cartController: CartController
    private var productId: Long = -1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // Ẩn ActionBar
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        productId = intent.getLongExtra("PRODUCT_ID", -1)
        if (productId == -1L) {
            finish()
            return
        }
        
        productController = ViewModelProvider(this)[ProductController::class.java]
        cartController = ViewModelProvider(this)[CartController::class.java]
        
        loadProduct()
        
        binding.btnAddToCart.setOnClickListener {
            cartController.addToCart(productId)
            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show()
        }
        
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
    
    private fun loadProduct() {
        lifecycleScope.launch {
            val product = productController.getProductById(productId)
            if (product != null) {
                binding.tvProductName.text = product.name
                binding.tvProductCode.text = "Mã SP: ${product.productCode}"
                binding.tvPrice.text = "${product.price.toInt()} VNĐ"
                binding.tvDescription.text = product.description
                binding.tvStock.text = "Tồn kho: ${product.stock}"
                
                ImageLoader.loadImage(binding.ivProductImage, product.imageUrl)
            }
        }
    }
}

