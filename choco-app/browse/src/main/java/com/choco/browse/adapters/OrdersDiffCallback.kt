package com.choco.browse.adapters

import androidx.recyclerview.widget.DiffUtil
import com.choco.browse.model.Order

class OrdersDiffCallback: DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}