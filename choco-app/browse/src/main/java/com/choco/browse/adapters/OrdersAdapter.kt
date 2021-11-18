package com.choco.browse.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.choco.browse.databinding.ItemOrderBinding
import com.choco.browse.model.Order
import com.choco.browse.view.viewholder.OrderViewHolder

class OrdersAdapter(private val onOrdersClickedListener: ((Order) -> Unit)): RecyclerView.Adapter<OrderViewHolder>() {
    private val differ = AsyncListDiffer(this, OrdersDiffCallback())

    fun setItems(list: List<Order>) {
        differ.submitList(list)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(differ.currentList[position], onOrdersClickedListener)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}