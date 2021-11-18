package com.choco.browse.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.choco.browse.adapters.ProductsEntriesDiffCallback
import com.choco.browse.databinding.ItemProductBinding
import com.choco.browse.model.Entry
import com.choco.browse.model.Product

class ProductViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        entry: Entry,
        onProductQuantityChangedListener: (Product, Int) -> Unit
    ) {
        binding.product = entry.product
        binding.onQuantityChangedListener = { quantity ->
            onProductQuantityChangedListener.invoke(entry.product, quantity)
        }
        binding.numberButton.currentValue = entry.quantity
        binding.executePendingBindings()
    }

    fun bindWithPayload(diff: ProductsEntriesDiffCallback.Diff) {
        if (diff.quantity != null)
            binding.numberButton.currentValue = diff.quantity
    }

    fun unbind() {
        binding.numberButton.onValueChangedListener = null
    }
}