package com.septemberfood.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.septemberfood.databinding.ItemCartBinding
import com.septemberfood.model.CartItem
import com.septemberfood.model.Product
import com.septemberfood.util.ImageLoader

class CartAdapter(
    private val onQuantityChange: (CartItem, Int) -> Unit,
    private val onRemove: (CartItem) -> Unit
) : ListAdapter<Pair<CartItem, Product>, CartAdapter.CartViewHolder>(CartDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(item: Pair<CartItem, Product>) {
            val (cartItem, product) = item
            
            binding.tvProductName.text = product.name
            binding.tvPrice.text = "${product.price.toInt()} VNĐ"
            binding.tvQuantity.text = cartItem.quantity.toString()
            binding.tvSubtotal.text = "${(product.price * cartItem.quantity).toInt()} VNĐ"
            
            ImageLoader.loadImage(binding.ivProductImage, product.imageUrl)
            
            binding.btnIncrease.setOnClickListener {
                onQuantityChange(cartItem, cartItem.quantity + 1)
            }
            
            binding.btnDecrease.setOnClickListener {
                if (cartItem.quantity > 1) {
                    onQuantityChange(cartItem, cartItem.quantity - 1)
                } else {
                    onRemove(cartItem)
                }
            }
            
            binding.btnRemove.setOnClickListener {
                onRemove(cartItem)
            }
        }
    }
    
    class CartDiffCallback : DiffUtil.ItemCallback<Pair<CartItem, Product>>() {
        override fun areItemsTheSame(
            oldItem: Pair<CartItem, Product>,
            newItem: Pair<CartItem, Product>
        ): Boolean {
            return oldItem.first.id == newItem.first.id
        }
        
        override fun areContentsTheSame(
            oldItem: Pair<CartItem, Product>,
            newItem: Pair<CartItem, Product>
        ): Boolean {
            return oldItem.first.quantity == newItem.first.quantity &&
                    oldItem.first.id == newItem.first.id
        }
    }
}

