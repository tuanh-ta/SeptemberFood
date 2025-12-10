package com.septemberfood.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.septemberfood.model.*

@Database(
    entities = [Product::class, CartItem::class, Order::class, OrderItem::class, User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
    abstract fun orderItemDao(): OrderItemDao
    abstract fun userDao(): UserDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "september_food_database"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // Tạm thời cho phép main thread để tránh crash
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

