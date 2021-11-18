package com.choco.browse.adapters

import androidx.recyclerview.widget.DiffUtil
import com.choco.browse.model.Entry

class ProductsEntriesDiffCallback: DiffUtil.ItemCallback<Entry>() {
    override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean {
        return oldItem.product.id == newItem.product.id
    }

    override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean {
        // It is a kotlin data class so .equal() is provided for free.
        return oldItem.product == newItem.product
                && oldItem.quantity == newItem.quantity
    }

    override fun getChangePayload(oldItem: Entry, newItem: Entry): Diff? {
        return if (oldItem.quantity != newItem.quantity)
             Diff(newItem.quantity)
        else
            null

    }
    data class Diff(val quantity: Int? = null)
}