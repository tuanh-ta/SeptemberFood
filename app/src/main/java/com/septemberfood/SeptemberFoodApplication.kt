package com.septemberfood

import android.app.Application
import androidx.lifecycle.lifecycleScope
import com.septemberfood.database.AppDatabase
import com.septemberfood.model.Product
import com.septemberfood.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SeptemberFoodApplication : Application() {
    
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    override fun onCreate() {
        super.onCreate()
        
        // Khởi tạo database và tạo dữ liệu mẫu
        applicationScope.launch {
            initializeDatabase()
        }
    }
    
    private suspend fun initializeDatabase() {
        val database = AppDatabase.getDatabase(this)
        val userDao = database.userDao()
        val productDao = database.productDao()
        
        // Tạo tài khoản customer nếu chưa có
        val customer = userDao.getUserByUsername("customer")
        if (customer == null) {
            userDao.insertUser(
                User(
                    username = "customer",
                    password = "customer123",
                    role = "Customer",
                    name = "Customer"
                )
            )
        }
        
        // Tạo hoặc cập nhật sản phẩm mẫu với ảnh mới
        // Gán ảnh từ img_1.jpg đến img_8.jpg cho 6 sản phẩm (đã random và cố định)
        val sampleProducts = listOf(
            Product(
                productCode = "PF001",
                name = "Thức ăn khô cho chó Royal Canin",
                description = "Thức ăn khô cao cấp cho chó trưởng thành, giàu protein và vitamin, giúp chó khỏe mạnh và phát triển tốt.",
                price = 450000.0,
                imageUrl = "img_3", // Ảnh img_3.jpg
                category = "Thức ăn cho chó",
                stock = 50
            ),
            Product(
                productCode = "PF002",
                name = "Thức ăn khô cho mèo Whiskas",
                description = "Thức ăn khô cho mèo trưởng thành, công thức đặc biệt với cá ngừ và gà, đảm bảo dinh dưỡng đầy đủ.",
                price = 320000.0,
                imageUrl = "img_7", // Ảnh img_7.jpg
                category = "Thức ăn cho mèo",
                stock = 40
            ),
            Product(
                productCode = "PF003",
                name = "Pate cho mèo Ciao",
                description = "Pate mềm thơm ngon cho mèo, giàu protein và omega-3, giúp mèo có bộ lông mượt mà.",
                price = 25000.0,
                imageUrl = "img_2", // Ảnh img_2.jpg
                category = "Thức ăn cho mèo",
                stock = 100
            ),
            Product(
                productCode = "PF004",
                name = "Xương gặm cho chó Pedigree",
                description = "Xương gặm tự nhiên giúp chó làm sạch răng và massage nướu, giảm mảng bám và hơi thở thơm mát.",
                price = 85000.0,
                imageUrl = "img_5", // Ảnh img_5.jpg
                category = "Đồ chơi cho chó",
                stock = 30
            ),
            Product(
                productCode = "PF005",
                name = "Thức ăn ướt cho chó Cesar",
                description = "Thức ăn ướt đóng hộp cho chó, nhiều hương vị đa dạng, giàu dinh dưỡng và dễ tiêu hóa.",
                price = 35000.0,
                imageUrl = "img_1", // Ảnh img_1.jpg
                category = "Thức ăn cho chó",
                stock = 80
            ),
            Product(
                productCode = "PF006",
                name = "Sữa bột cho chó con Royal Canin",
                description = "Sữa bột công thức đặc biệt cho chó con, thay thế sữa mẹ, giàu DHA và các chất dinh dưỡng cần thiết.",
                price = 280000.0,
                imageUrl = "img_4", // Ảnh img_4.jpg
                category = "Thức ăn cho chó",
                stock = 25
            )
        )
        
        // Cập nhật hoặc tạo mới sản phẩm mẫu
        sampleProducts.forEach { newProduct ->
            val existingProduct = productDao.getProductByCode(newProduct.productCode)
            if (existingProduct != null) {
                // Cập nhật sản phẩm đã tồn tại với ảnh mới
                val updatedProduct = existingProduct.copy(
                    imageUrl = newProduct.imageUrl,
                    name = newProduct.name,
                    description = newProduct.description,
                    price = newProduct.price,
                    category = newProduct.category,
                    stock = newProduct.stock
                )
                productDao.updateProduct(updatedProduct)
            } else {
                // Tạo mới nếu chưa có
                productDao.insertProduct(newProduct)
            }
        }
    }
}

