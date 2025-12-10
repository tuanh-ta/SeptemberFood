package com.septemberfood.view.admin

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.septemberfood.databinding.DialogAddProductBinding
import com.septemberfood.model.Product

class AddProductDialogFragment : DialogFragment() {
    private var _binding: DialogAddProductBinding? = null
    private val binding get() = _binding!!
    var onProductAdded: ((Product) -> Unit)? = null
    private var editingProduct: Product? = null
    
    companion object {
        fun newInstance(product: Product): AddProductDialogFragment {
            val fragment = AddProductDialogFragment()
            fragment.editingProduct = product
            return fragment
        }
    }
    
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        if (editingProduct != null) {
            binding.etProductCode.setText(editingProduct!!.productCode)
            binding.etProductName.setText(editingProduct!!.name)
            binding.etDescription.setText(editingProduct!!.description)
            binding.etPrice.setText(editingProduct!!.price.toString())
            binding.etImageUrl.setText(editingProduct!!.imageUrl)
            binding.etStock.setText(editingProduct!!.stock.toString())
            binding.btnSave.text = "Cập nhật"
        }
        
        binding.btnSave.setOnClickListener {
            val productCode = binding.etProductCode.text.toString().trim()
            val name = binding.etProductName.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()
            val priceStr = binding.etPrice.text.toString().trim()
            val imageUrl = binding.etImageUrl.text.toString().trim()
            val stockStr = binding.etStock.text.toString().trim()
            
            if (productCode.isEmpty() || name.isEmpty() || description.isEmpty() || priceStr.isEmpty()) {
                android.widget.Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", android.widget.Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            val price = priceStr.toDoubleOrNull() ?: 0.0
            val stock = stockStr.toIntOrNull() ?: 0
            
            val product = if (editingProduct != null) {
                editingProduct!!.copy(
                    productCode = productCode,
                    name = name,
                    description = description,
                    price = price,
                    imageUrl = imageUrl,
                    stock = stock
                )
            } else {
                Product(
                    productCode = productCode,
                    name = name,
                    description = description,
                    price = price,
                    imageUrl = imageUrl,
                    stock = stock
                )
            }
            
            onProductAdded?.invoke(product)
            dismiss()
        }
        
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

