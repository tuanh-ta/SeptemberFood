package com.septemberfood.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.septemberfood.database.AppDatabase
import com.septemberfood.database.ProductDao
import com.septemberfood.model.Product
import kotlinx.coroutines.launch

class ProductController(application: Application) : AndroidViewModel(application) {
    private val productDao: ProductDao = AppDatabase.getDatabase(application).productDao()
    
    val allProducts: LiveData<List<Product>> = productDao.getAllProducts()
    
    fun searchProducts(query: String): LiveData<List<Product>> {
        return productDao.searchProducts(query)
    }
    
    fun getProductsByCategory(category: String): LiveData<List<Product>> {
        return productDao.getProductsByCategory(category)
    }
    
    suspend fun getAllCategories(): List<String> {
        return productDao.getAllCategories()
    }
    
    fun insertProduct(product: Product) {
        viewModelScope.launch {
            productDao.insertProduct(product)
        }
    }
    
    fun updateProduct(product: Product) {
        viewModelScope.launch {
            productDao.updateProduct(product)
        }
    }
    
    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            productDao.deleteProduct(product)
        }
    }
    
    suspend fun getProductById(id: Long): Product? {
        return productDao.getProductById(id)
    }
}

