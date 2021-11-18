package com.choco.browse.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.choco.browse.databinding.ItemEntryPreviewBinding
import com.choco.browse.model.Entry

class EntryPreviewViewHolder(private val binding: ItemEntryPreviewBinding)
    : RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: Entry) {
            binding.apply {
                val total = entry.product.price * entry.quantity
                name = String.format("%d X %d", entry.product.price, entry.quantity)
                quantities = entry.product.name
                this.total = "$total"
                executePendingBindings()
            }
        }
}