package com.septemberfood.view.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.septemberfood.R
import com.septemberfood.controller.ProductController
import com.septemberfood.databinding.ActivityAdminBinding
import com.septemberfood.model.Product
import com.septemberfood.util.UserSession
import com.septemberfood.view.MainActivity
import com.septemberfood.view.adapter.AdminProductAdapter
import com.septemberfood.view.customer.ProfileActivity

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private lateinit var productController: ProductController
    private lateinit var adapter: AdminProductAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // Ẩn ActionBar
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        productController = ViewModelProvider(this)[ProductController::class.java]
        
        setupRecyclerView()
        observeProducts()
        setupToolbar()
        
        binding.fabAddProduct.setOnClickListener {
            showAddProductDialog()
        }
    }
    
    private fun setupToolbar() {
        binding.btnBack.setOnClickListener {
            finish()
        }
        
        binding.ivProfileIcon.setOnClickListener {
            showProfileMenu(it)
        }
    }
    
    private fun showProfileMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_profile, popupMenu.menu)
        
        // Admin luôn đã đăng nhập, ẩn menu đăng nhập
        popupMenu.menu.findItem(R.id.menu_login)?.isVisible = false
        popupMenu.menu.findItem(R.id.menu_profile_info)?.isVisible = true
        popupMenu.menu.findItem(R.id.menu_logout)?.isVisible = true
        
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_profile_info -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                R.id.menu_logout -> {
                    logout()
                    true
                }
                else -> false
            }
        }
        
        popupMenu.show()
    }
    
    private fun logout() {
        UserSession.clearUser(this)
        val intent = Intent(this, com.septemberfood.view.customer.ProductListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    private fun setupRecyclerView() {
        adapter = AdminProductAdapter(
            onEditClick = { product ->
                showEditProductDialog(product)
            },
            onDeleteClick = { product ->
                productController.deleteProduct(product)
                Toast.makeText(this, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show()
            }
        )
        
        binding.recyclerViewProducts.apply {
            layoutManager = LinearLayoutManager(this@AdminActivity)
            adapter = this@AdminActivity.adapter
        }
    }
    
    private fun observeProducts() {
        productController.allProducts.observe(this) { products ->
            adapter.submitList(products)
        }
    }
    
    private fun showAddProductDialog() {
        val dialog = AddProductDialogFragment()
        dialog.onProductAdded = { product ->
            productController.insertProduct(product)
            Toast.makeText(this, "Đã thêm sản phẩm", Toast.LENGTH_SHORT).show()
        }
        dialog.show(supportFragmentManager, "AddProductDialog")
    }
    
    private fun showEditProductDialog(product: Product) {
        val dialog = AddProductDialogFragment.newInstance(product)
        dialog.onProductAdded = { updatedProduct ->
            productController.updateProduct(updatedProduct)
            Toast.makeText(this, "Đã cập nhật sản phẩm", Toast.LENGTH_SHORT).show()
        }
        dialog.show(supportFragmentManager, "EditProductDialog")
    }
}

