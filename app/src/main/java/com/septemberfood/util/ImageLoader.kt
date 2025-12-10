package com.septemberfood.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.septemberfood.R

object ImageLoader {
    /**
     * Load image from drawable resource or URL
     * If imageUrl starts with "drawable:" or doesn't start with "http", it will be treated as drawable resource name
     * Otherwise, it will be loaded from URL
     */
    fun loadImage(imageView: ImageView, imageUrl: String) {
        if (imageUrl.isEmpty()) {
            imageView.setImageResource(R.drawable.ic_placeholder)
            return
        }
        
        // Check if it's a drawable resource
        val drawableId = getDrawableResourceId(imageView.context, imageUrl)
        if (drawableId != null) {
            // Load from drawable
            Glide.with(imageView.context)
                .load(drawableId)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(imageView)
        } else {
            // Load from URL
            Glide.with(imageView.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(imageView)
        }
    }
    
    /**
     * Get drawable resource ID from resource name
     * Returns null if resource not found or if it's a URL
     */
    private fun getDrawableResourceId(context: android.content.Context, resourceName: String): Int? {
        // If it starts with http/https, it's definitely a URL
        if (resourceName.startsWith("http://") || resourceName.startsWith("https://")) {
            return null
        }
        
        // Remove "drawable:" prefix if present
        val cleanName = resourceName.removePrefix("drawable:")
        
        // Try to get resource ID
        return try {
            val resourceId = context.resources.getIdentifier(
                cleanName,
                "drawable",
                context.packageName
            )
            if (resourceId != 0) resourceId else null
        } catch (e: Exception) {
            null
        }
    }
}

