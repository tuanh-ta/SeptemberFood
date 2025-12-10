package com.septemberfood.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.septemberfood.databinding.ItemProductBinding
import com.septemberfood.model.Product

class ProductAdapter(
    private val onItemClick: (Product) -> Unit,
    private val onAddToCart: (Product) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(product: Product) {
            binding.tvProductName.text = product.name
            binding.tvPrice.text = "${product.price.toInt()} VNĐ"
            
            if (product.imageUrl.isNotEmpty()) {
                Glide.with(binding.root.context)
                    .load(product.imageUrl)
                    .placeholder(com.septemberfood.R.drawable.ic_placeholder)
                    .into(binding.ivProductImage)
            }
            
            binding.root.setOnClickListener {
                onItemClick(product)
            }
            
            binding.btnAddToCart.setOnClickListener {
                onAddToCart(product)
            }
        }
    }
    
    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}

