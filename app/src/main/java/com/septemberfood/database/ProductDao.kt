package com.septemberfood.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.septemberfood.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM products ORDER BY createdAt DESC")
    fun getAllProducts(): LiveData<List<Product>>
    
    @Query("SELECT * FROM products WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' OR productCode LIKE '%' || :query || '%'")
    fun searchProducts(query: String): LiveData<List<Product>>
    
    @Query("SELECT * FROM products WHERE category = :category ORDER BY createdAt DESC")
    fun getProductsByCategory(category: String): LiveData<List<Product>>
    
    @Query("SELECT DISTINCT category FROM products")
    suspend fun getAllCategories(): List<String>
    
    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Long): Product?
    
    @Query("SELECT * FROM products WHERE productCode = :productCode")
    suspend fun getProductByCode(productCode: String): Product?
    
    @Query("SELECT COUNT(*) FROM products")
    suspend fun getProductCount(): Int
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product): Long
    
    @Update
    suspend fun updateProduct(product: Product)
    
    @Delete
    suspend fun deleteProduct(product: Product)
    
    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteProductById(id: Long)
}

