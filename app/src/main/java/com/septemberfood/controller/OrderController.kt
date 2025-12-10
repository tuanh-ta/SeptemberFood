package com.septemberfood.controller

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.septemberfood.database.AppDatabase
import com.septemberfood.database.CartDao
import com.septemberfood.database.OrderDao
import com.septemberfood.database.OrderItemDao
import com.septemberfood.database.ProductDao
import com.septemberfood.model.CartItem
import com.septemberfood.model.Order
import com.septemberfood.model.OrderItem
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class OrderController(application: Application) : AndroidViewModel(application) {
    private val orderDao: OrderDao = AppDatabase.getDatabase(application).orderDao()
    private val orderItemDao: OrderItemDao = AppDatabase.getDatabase(application).orderItemDao()
    private val cartDao: CartDao = AppDatabase.getDatabase(application).cartDao()
    private val productDao: ProductDao = AppDatabase.getDatabase(application).productDao()
    
    val allOrders: LiveData<List<Order>> = orderDao.getAllOrders()
    
    fun createOrder(
        customerName: String,
        customerPhone: String,
        customerAddress: String,
        cartItems: List<CartItem>,
        onComplete: (Long) -> Unit
    ) {
        viewModelScope.launch {
            if (cartItems.isEmpty()) {
                onComplete(-1)
                return@launch
            }
            
            var totalAmount = 0.0
            val orderItemData = mutableListOf<Triple<Long, Int, Double>>() // productId, quantity, price
            
            for (item in cartItems) {
                val product = productDao.getProductById(item.productId)
                if (product != null) {
                    totalAmount += product.price * item.quantity
                    orderItemData.add(Triple(product.id, item.quantity, product.price))
                }
            }
            
            val orderNumber = generateOrderNumber()
            val order = Order(
                orderNumber = orderNumber,
                totalAmount = totalAmount,
                customerName = customerName,
                customerPhone = customerPhone,
                customerAddress = customerAddress,
                paymentMethod = "Cash",
                status = "Pending"
            )
            
            val orderId = orderDao.insertOrder(order)
            
            // Create order items with orderId
            val orderItems = orderItemData.map { (productId, quantity, price) ->
                OrderItem(
                    orderId = orderId,
                    productId = productId,
                    quantity = quantity,
                    price = price
                )
            }
            orderItemDao.insertOrderItems(orderItems)
            
            // Clear cart
            cartDao.clearCart()
            
            onComplete(orderId)
        }
    }
    
    private fun generateOrderNumber(): String {
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
        return "ORD-${dateFormat.format(Date())}"
    }
}

