package com.septemberfood.view.customer

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.septemberfood.R
import com.septemberfood.controller.CartController
import com.septemberfood.controller.ProductController
import com.septemberfood.databinding.ActivityProductListBinding
import com.septemberfood.model.Product
import com.septemberfood.util.UserSession
import com.septemberfood.view.MainActivity
import com.septemberfood.view.adapter.ProductAdapter
import kotlinx.coroutines.launch

class ProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductListBinding
    private lateinit var productController: ProductController
    private lateinit var cartController: CartController
    private lateinit var productAdapter: ProductAdapter
    private var allProducts: List<Product> = emptyList()
    private var currentCategory: String? = null
    private var currentSearchQuery: String = ""
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // Ẩn ActionBar
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        productController = ViewModelProvider(this)[ProductController::class.java]
        cartController = ViewModelProvider(this)[CartController::class.java]
        
        setupRecyclerView()
        observeProducts()
        setupSearchView()
        setupCategoryChips()
        setupToolbar()
        
    }
    
    private fun setupToolbar() {
        binding.btnBack.setOnClickListener {
            finish()
        }
        
        binding.ivCartIcon.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        
        binding.ivProfileIcon.setOnClickListener {
            showProfileMenu(it)
        }
    }
    
    private fun showProfileMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_profile, popupMenu.menu)
        
        // Hiển thị menu động dựa trên trạng thái đăng nhập
        val isLoggedIn = UserSession.isLoggedIn(this)
        popupMenu.menu.findItem(R.id.menu_login)?.isVisible = !isLoggedIn
        popupMenu.menu.findItem(R.id.menu_profile_info)?.isVisible = isLoggedIn
        popupMenu.menu.findItem(R.id.menu_logout)?.isVisible = isLoggedIn
        
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_login -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
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
        // Không cần chuyển màn hình, chỉ cần refresh menu
        Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show()
    }
    
    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(
            onItemClick = { product ->
                val intent = Intent(this, ProductDetailActivity::class.java)
                intent.putExtra("PRODUCT_ID", product.id)
                startActivity(intent)
            },
            onAddToCart = { product ->
                cartController.addToCart(product.id)
                Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show()
            }
        )
        
        binding.recyclerViewProducts.apply {
            layoutManager = GridLayoutManager(this@ProductListActivity, 2)
            adapter = productAdapter
        }
    }
    
    private fun observeProducts() {
        productController.allProducts.observe(this) { products ->
            allProducts = products
            filterAndDisplayProducts()
            binding.progressBar.visibility = View.GONE
        }
    }
    
    private fun setupSearchView() {
        // Cải thiện UI của SearchView
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }
            
            override fun onQueryTextChange(newText: String?): Boolean {
                currentSearchQuery = newText ?: ""
                filterAndDisplayProducts()
                return true
            }
        })
        
        // Đảm bảo SearchView luôn ở trạng thái expanded nhưng không tự động focus
        searchView.isIconified = false
        searchView.onActionViewExpanded()
        
        // Ẩn tất cả icon mặc định của SearchView vì đã có icon riêng
        // Cần thực hiện sau khi SearchView đã được render
        searchView.post {
            val searchIcon = searchView.findViewById<View>(androidx.appcompat.R.id.search_mag_icon)
            searchIcon?.visibility = View.GONE
            
            // Ẩn icon close nếu có
            val closeButton = searchView.findViewById<View>(androidx.appcompat.R.id.search_close_btn)
            closeButton?.visibility = View.GONE
            
            // Xóa focus để không tự động mở bàn phím khi vào trang
            searchView.clearFocus()
            
            // Cho phép focus khi người dùng click vào
            val searchEditText = searchView.findViewById<android.widget.EditText>(androidx.appcompat.R.id.search_src_text)
            searchEditText?.isFocusable = true
            searchEditText?.isFocusableInTouchMode = true
        }
        
        // Set màu text cho SearchView
        val searchEditText = searchView.findViewById<android.widget.EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText?.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
        searchEditText?.setHintTextColor(ContextCompat.getColor(this, R.color.text_secondary))
    }
    
    private fun setupCategoryChips() {
        lifecycleScope.launch {
            val categories = productController.getAllCategories()
            val categoryContainer = binding.categoryContainer
            
            // Chip "Tất cả" đã có trong layout
            binding.chipAll.setOnClickListener {
                selectCategory(null)
            }
            
            // Thêm các chip danh mục
            categories.forEach { category ->
                val chip = Chip(this@ProductListActivity).apply {
                    text = category
                    isCheckable = true
                    isChecked = false
                    setChipBackgroundColorResource(R.color.chip_background)
                    setTextColor(ContextCompat.getColor(this@ProductListActivity, R.color.text_primary))
                    setChipStrokeColorResource(R.color.chip_stroke)
                    chipStrokeWidth = 1f
                    layoutParams = android.view.ViewGroup.MarginLayoutParams(
                        android.view.ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                        android.view.ViewGroup.MarginLayoutParams.WRAP_CONTENT
                    ).apply {
                        marginEnd = resources.getDimensionPixelSize(R.dimen.chip_margin)
                    }
                    setOnClickListener {
                        selectCategory(category)
                    }
                }
                categoryContainer.addView(chip)
            }
        }
    }
    
    private fun selectCategory(category: String?) {
        currentCategory = category
        
        // Reset và highlight chip được chọn
        binding.chipAll.isChecked = category == null
        if (category == null) {
            binding.chipAll.setChipBackgroundColorResource(R.color.chip_selected_background)
            binding.chipAll.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        } else {
            binding.chipAll.setChipBackgroundColorResource(R.color.chip_background)
            binding.chipAll.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
        }
        
        for (i in 1 until binding.categoryContainer.childCount) {
            val chip = binding.categoryContainer.getChildAt(i) as? Chip
            val chipCategory = chip?.text.toString()
            chip?.isChecked = chipCategory == category
            
            if (chipCategory == category) {
                chip?.setChipBackgroundColorResource(R.color.chip_selected_background)
                chip?.setTextColor(ContextCompat.getColor(this, android.R.color.white))
            } else {
                chip?.setChipBackgroundColorResource(R.color.chip_background)
                chip?.setTextColor(ContextCompat.getColor(this, R.color.text_primary))
            }
        }
        
        filterAndDisplayProducts()
    }
    
    private fun filterAndDisplayProducts() {
        var filtered = allProducts
        
        // Lọc theo danh mục
        if (!currentCategory.isNullOrEmpty()) {
            filtered = filtered.filter { it.category == currentCategory }
        }
        
        // Lọc theo tìm kiếm
        if (currentSearchQuery.isNotEmpty()) {
            filtered = filtered.filter {
                it.name.contains(currentSearchQuery, ignoreCase = true) ||
                it.description.contains(currentSearchQuery, ignoreCase = true) ||
                it.productCode.contains(currentSearchQuery, ignoreCase = true)
            }
        }
        
        productAdapter.submitList(filtered)
        
        if (filtered.isEmpty()) {
            binding.tvEmpty.visibility = View.VISIBLE
            binding.tvEmpty.text = if (currentSearchQuery.isNotEmpty()) {
                "Không tìm thấy sản phẩm \"$currentSearchQuery\""
            } else {
                "Không có sản phẩm nào"
            }
        } else {
            binding.tvEmpty.visibility = View.GONE
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_list, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

