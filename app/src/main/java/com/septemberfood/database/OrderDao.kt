package com.septemberfood.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.septemberfood.model.Order

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders ORDER BY createdAt DESC")
    fun getAllOrders(): LiveData<List<Order>>
    
    @Query("SELECT * FROM orders WHERE id = :id")
    suspend fun getOrderById(id: Long): Order?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order): Long
    
    @Update
    suspend fun updateOrder(order: Order)
    
    @Delete
    suspend fun deleteOrder(order: Order)
}

