package com.septemberfood.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.septemberfood.database.AppDatabase
import com.septemberfood.database.CartDao
import com.septemberfood.database.ProductDao
import com.septemberfood.model.CartItem
import com.septemberfood.model.Product
import kotlinx.coroutines.launch

class CartController(application: Application) : AndroidViewModel(application) {
    private val cartDao: CartDao = AppDatabase.getDatabase(application).cartDao()
    private val productDao: ProductDao = AppDatabase.getDatabase(application).productDao()
    
    val allCartItems: LiveData<List<CartItem>> = cartDao.getAllCartItems()
    
    fun addToCart(productId: Long) {
        viewModelScope.launch {
            val existingItem = cartDao.getCartItemByProductId(productId)
            if (existingItem != null) {
                cartDao.updateCartItem(existingItem.copy(quantity = existingItem.quantity + 1))
            } else {
                cartDao.insertCartItem(CartItem(productId = productId, quantity = 1))
            }
        }
    }
    
    fun updateCartItemQuantity(cartItem: CartItem, newQuantity: Int) {
        viewModelScope.launch {
            if (newQuantity > 0) {
                cartDao.updateCartItem(cartItem.copy(quantity = newQuantity))
            } else {
                cartDao.deleteCartItem(cartItem)
            }
        }
    }
    
    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            cartDao.deleteCartItem(cartItem)
        }
    }
    
    fun clearCart() {
        viewModelScope.launch {
            cartDao.clearCart()
        }
    }
    
    suspend fun getCartTotal(cartItems: List<CartItem>): Double {
        var total = 0.0
        for (item in cartItems) {
            val product = productDao.getProductById(item.productId)
            if (product != null) {
                total += product.price * item.quantity
            }
        }
        return total
    }
    
    suspend fun getCartItemsWithProducts(cartItems: List<CartItem>): List<Pair<CartItem, Product>> {
        return cartItems.mapNotNull { item ->
            val product = productDao.getProductById(item.productId)
            product?.let { Pair(item, it) }
        }
    }
}

