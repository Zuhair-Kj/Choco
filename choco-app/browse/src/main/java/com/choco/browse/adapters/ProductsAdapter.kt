package com.choco.browse.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.choco.browse.databinding.ItemProductBinding
import com.choco.browse.model.Entry
import com.choco.browse.model.Product
import com.choco.browse.view.viewholder.ProductViewHolder

class ProductsAdapter(
    private val onProductQuantityChangedListener: (Product, Int) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {
    private val differ = AsyncListDiffer(this, ProductsEntriesDiffCallback())

    fun setItems(list: List<Entry>) {
        differ.submitList(ArrayList(list))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(differ.currentList[position], onProductQuantityChangedListener)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            (payloads[0] as? ProductsEntriesDiffCallback.Diff)?.let {
                holder.bindWithPayload(it)
            } ?: kotlin.run {
                super.onBindViewHolder(holder, position, payloads)
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: ProductViewHolder) {
        holder.unbind()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}