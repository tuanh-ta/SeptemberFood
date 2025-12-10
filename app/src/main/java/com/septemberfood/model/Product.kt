package com.septemberfood.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val productCode: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String = "Pet Food",
    val stock: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)

