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
        
        // Tạo tài khoản admin nếu chưa có
        val admin = userDao.getUserByUsername("admin")
        if (admin == null) {
            userDao.insertUser(
                User(
                    username = "admin",
                    password = "admin123",
                    role = "Admin",
                    name = "Administrator"
                )
            )
        }
        
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
        
        // Tạo một số sản phẩm mẫu nếu chưa có
        // Kiểm tra số lượng sản phẩm hiện có
        val productCount = productDao.getProductCount()
        if (productCount == 0) {
            val sampleProducts = listOf(
                Product(
                    productCode = "PF001",
                    name = "Thức ăn khô cho chó Royal Canin",
                    description = "Thức ăn khô cao cấp cho chó trưởng thành, giàu protein và vitamin, giúp chó khỏe mạnh và phát triển tốt.",
                    price = 450000.0,
                    imageUrl = "https://via.placeholder.com/300x300?text=Royal+Canin",
                    category = "Thức ăn cho chó",
                    stock = 50
                ),
                Product(
                    productCode = "PF002",
                    name = "Thức ăn khô cho mèo Whiskas",
                    description = "Thức ăn khô cho mèo trưởng thành, công thức đặc biệt với cá ngừ và gà, đảm bảo dinh dưỡng đầy đủ.",
                    price = 320000.0,
                    imageUrl = "https://via.placeholder.com/300x300?text=Whiskas",
                    category = "Thức ăn cho mèo",
                    stock = 40
                ),
                Product(
                    productCode = "PF003",
                    name = "Pate cho mèo Ciao",
                    description = "Pate mềm thơm ngon cho mèo, giàu protein và omega-3, giúp mèo có bộ lông mượt mà.",
                    price = 25000.0,
                    imageUrl = "https://via.placeholder.com/300x300?text=Ciao+Pate",
                    category = "Thức ăn cho mèo",
                    stock = 100
                ),
                Product(
                    productCode = "PF004",
                    name = "Xương gặm cho chó Pedigree",
                    description = "Xương gặm tự nhiên giúp chó làm sạch răng và massage nướu, giảm mảng bám và hơi thở thơm mát.",
                    price = 85000.0,
                    imageUrl = "https://via.placeholder.com/300x300?text=Pedigree+Bone",
                    category = "Đồ chơi cho chó",
                    stock = 30
                ),
                Product(
                    productCode = "PF005",
                    name = "Thức ăn ướt cho chó Cesar",
                    description = "Thức ăn ướt đóng hộp cho chó, nhiều hương vị đa dạng, giàu dinh dưỡng và dễ tiêu hóa.",
                    price = 35000.0,
                    imageUrl = "https://via.placeholder.com/300x300?text=Cesar+Wet",
                    category = "Thức ăn cho chó",
                    stock = 80
                ),
                Product(
                    productCode = "PF006",
                    name = "Sữa bột cho chó con Royal Canin",
                    description = "Sữa bột công thức đặc biệt cho chó con, thay thế sữa mẹ, giàu DHA và các chất dinh dưỡng cần thiết.",
                    price = 280000.0,
                    imageUrl = "https://via.placeholder.com/300x300?text=Royal+Canin+Milk",
                    category = "Thức ăn cho chó",
                    stock = 25
                )
            )
            
            sampleProducts.forEach { product ->
                productDao.insertProduct(product)
            }
        }
    }
}

