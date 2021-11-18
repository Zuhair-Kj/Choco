package com.choco.browse.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.choco.browse.databinding.ItemOrderBinding
import com.choco.browse.model.Order
import java.text.SimpleDateFormat
import java.util.*

class OrderViewHolder(val binding: ItemOrderBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(order: Order, onOrdersClickedListener: ((Order) -> Unit)) {
        binding.apply {
            textOrderNumber.text = String.format("#%s", order.id)
            textDate.text = parseDate(order.date)
            root.setOnClickListener { onOrdersClickedListener(order) }
        }
    }

    private fun parseDate(long: Long): String {
        return SimpleDateFormat("dd-MMM-yyyy hh:mm", Locale.ENGLISH).format(Date(long))
    }
}