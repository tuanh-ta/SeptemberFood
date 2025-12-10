package com.septemberfood.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.septemberfood.model.OrderItem

@Dao
interface OrderItemDao {
    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    fun getOrderItemsByOrderId(orderId: Long): LiveData<List<OrderItem>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItem(orderItem: OrderItem): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(orderItems: List<OrderItem>)
}

