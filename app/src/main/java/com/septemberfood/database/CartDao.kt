package com.septemberfood.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.septemberfood.model.CartItem

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): LiveData<List<CartItem>>
    
    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    suspend fun getCartItemByProductId(productId: Long): CartItem?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem): Long
    
    @Update
    suspend fun updateCartItem(cartItem: CartItem)
    
    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)
    
    @Query("DELETE FROM cart_items WHERE id = :id")
    suspend fun deleteCartItemById(id: Long)
    
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
    
    @Query("SELECT COUNT(*) FROM cart_items")
    suspend fun getCartItemCount(): Int
}

