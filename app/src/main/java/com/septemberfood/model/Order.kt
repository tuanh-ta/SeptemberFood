package com.septemberfood.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderNumber: String,
    val totalAmount: Double,
    val status: String = "Pending", // Pending, Completed, Cancelled
    val paymentMethod: String = "Cash",
    val customerName: String = "",
    val customerPhone: String = "",
    val customerAddress: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

