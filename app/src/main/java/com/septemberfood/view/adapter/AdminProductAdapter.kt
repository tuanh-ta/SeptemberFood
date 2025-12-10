package com.septemberfood.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.septemberfood.databinding.ItemAdminProductBinding
import com.septemberfood.model.Product

class AdminProductAdapter(
    private val onEditClick: (Product) -> Unit,
    private val onDeleteClick: (Product) -> Unit
) : ListAdapter<Product, AdminProductAdapter.AdminProductViewHolder>(ProductDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminProductViewHolder {
        val binding = ItemAdminProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AdminProductViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: AdminProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class AdminProductViewHolder(private val binding: ItemAdminProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(product: Product) {
            binding.tvProductName.text = product.name
            binding.tvProductCode.text = "Mã: ${product.productCode}"
            binding.tvPrice.text = "${product.price.toInt()} VNĐ"
            binding.tvStock.text = "Tồn kho: ${product.stock}"
            
            if (product.imageUrl.isNotEmpty()) {
                Glide.with(binding.root.context)
                    .load(product.imageUrl)
                    .placeholder(com.septemberfood.R.drawable.ic_placeholder)
                    .into(binding.ivProductImage)
            }
            
            binding.btnEdit.setOnClickListener {
                onEditClick(product)
            }
            
            binding.btnDelete.setOnClickListener {
                onDeleteClick(product)
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

