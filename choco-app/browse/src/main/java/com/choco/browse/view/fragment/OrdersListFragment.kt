package com.choco.browse.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.choco.browse.R
import com.choco.browse.adapters.OrdersAdapter
import com.choco.browse.databinding.FragmentOrdersListBinding
import com.choco.browse.model.Order
import com.choco.browse.view.VerticalRowsDecorator
import com.choco.browse.viewmodel.OrdersViewModel
import com.choco.core.extensions.hide
import com.choco.core.extensions.show
import com.choco.core.util.Resource
import com.choco.core.util.Resource.Status.SUCCESS
import com.choco.core.util.Resource.Status.ERROR
import com.choco.core.util.Resource.Status.LOADING
import com.choco.core_ui.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrdersListFragment : BaseFragment<FragmentOrdersListBinding>() {
    private val viewModel: OrdersViewModel by viewModel()
    private val bridge: Bridge by inject()
    override fun getLayoutId() = R.layout.fragment_orders_list
    private val ordersAdapter by lazy {
        OrdersAdapter(onOrderClickedListener)
    }

    private val verticalRowsDecorator by lazy {
        VerticalRowsDecorator(
            verticalOffset = requireActivity().resources.getDimensionPixelOffset(R.dimen.margin_large),
            horizontalOffset = requireActivity().resources.getDimensionPixelOffset(R.dimen.margin_medium)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
        viewModel.ordersLiveData.observe(viewLifecycleOwner, ordersObserver)
        viewModel.getOrders()
    }

    private fun initUI() {
        binding?.apply {
            recyclerView.adapter = ordersAdapter
            recyclerView.layoutManager = LinearLayoutManager(root.context)
            recyclerView.addItemDecoration(verticalRowsDecorator)

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.getOrders()
            }
        }
    }

    private val ordersObserver = Observer<Resource<List<Order>>> { resource ->
        when (resource?.status) {
            SUCCESS -> {
                val orders = resource.data ?: emptyList()
                ordersAdapter.setItems(orders)
                if (orders.isEmpty())
                    showEmpty()
                else {
                    showResults()
                }
            }
            ERROR -> {
                showError()
            }

            LOADING -> {
                showLoading()
            }
            else -> {

            }
        }
    }

    private fun showResults() {
        binding?.apply {
            swipeRefreshLayout.isRefreshing = false
            loadingLayout.root.hide()
            recyclerView.show()
            groupError.hide()
            textEmpty.hide()
        }
    }

    private fun showEmpty() {
        binding?.apply {
            swipeRefreshLayout.isRefreshing = false
            loadingLayout.root.hide()
            textEmpty.show()
            recyclerView.hide()
            groupError.hide()
        }
    }

    private fun showError() {
        binding?.apply {
            swipeRefreshLayout.isRefreshing = false
            loadingLayout.root.hide()
            textEmpty.hide()
            recyclerView.hide()
            groupError.show()
        }
    }

    private fun showLoading() {
        binding?.apply {
            if (ordersAdapter.itemCount == 0) {
                loadingLayout.root.show()
                recyclerView.hide()
                textEmpty.hide()
                groupError.hide()
            }
        }
    }

    private val onOrderClickedListener = { order: Order ->
        bridge.openOrderDetailsPage(order)
    }

    interface Bridge {
        fun openOrderDetailsPage(order: Order)
    }
}