package com.choco.browse.view.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.choco.browse.R
import com.choco.browse.adapters.OrderEntriesAdapter
import com.choco.browse.databinding.FragmentOrderDetailsBinding
import com.choco.browse.model.Entry
import com.choco.browse.model.Order
import com.choco.core_ui.BaseFragment

class OrderDetailsFragment: BaseFragment<FragmentOrderDetailsBinding> (){
    override fun getLayoutId() = R.layout.fragment_order_details

    private val order: Order? by lazy { arguments?.getParcelable(KEY_ORDER) }
    private val orderEntriesAdapter = OrderEntriesAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            textOrderNumber.text = String.format("%s #%s", getString(R.string.order), order?.id)
            val grandTotal = order?.entries?.fold(0) { acc, entry ->
                acc + (entry.quantity * entry.product.price)
            } ?: 0
            populateEntries(order?.entries ?: emptyList())
            textGrandTotal.text = getString(R.string.total_formatted, grandTotal)
        }
    }

    private fun populateEntries(entries: List<Entry>) {
        binding?.recyclerView?.apply {
            adapter = orderEntriesAdapter
            orderEntriesAdapter.items = entries
            layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        const val KEY_ORDER = "order"
    }
}