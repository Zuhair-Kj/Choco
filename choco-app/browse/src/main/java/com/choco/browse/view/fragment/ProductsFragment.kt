package com.choco.browse.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.choco.browse.R
import com.choco.browse.adapters.ProductsAdapter
import com.choco.browse.databinding.FragmentProductsBinding
import com.choco.browse.model.Entry
import com.choco.browse.model.Product
import com.choco.browse.view.VerticalRowsDecorator
import com.choco.browse.viewmodel.ProductsViewModel
import com.choco.core.extensions.hide
import com.choco.core.extensions.show
import com.choco.core.util.Resource
import com.choco.core.util.Resource.Status.*
import com.choco.core_ui.BaseFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named

class ProductsFragment: BaseFragment<FragmentProductsBinding>() {
    private val viewModel: ProductsViewModel by sharedViewModel(named("ProductsFragment"))
    private val productsAdapter: ProductsAdapter by lazy {
        ProductsAdapter(onProductQuantityChangedListener)
    }
    private val verticalRowsDecorator: VerticalRowsDecorator by lazy {
        VerticalRowsDecorator(
            requireActivity().resources.getDimensionPixelOffset(R.dimen.margin_medium),
            requireActivity().resources.getDimensionPixelOffset(R.dimen.margin_small)
        )
    }
    override fun getLayoutId() = R.layout.fragment_products

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
        viewModel.productsLiveData.observe(viewLifecycleOwner, productsObserver)
        viewModel.createOrderLiveData.observe(viewLifecycleOwner, createOrderObserver)
        if (viewModel.productsLiveData.value?.data?.isEmpty() != false) {
            viewModel.getProducts()
        }
    }

    private fun initUi() {
        binding?.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireActivity())
            recyclerView.adapter = productsAdapter
            recyclerView.addItemDecoration(verticalRowsDecorator)
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.getProducts()
            }
            buttonPlaceOrder.setOnClickListener {
                viewModel.createOrder()
            }
        }
    }

    private val productsObserver = Observer<Resource<List<Entry>>> { resource ->
        when(resource?.status) {
            SUCCESS -> {
                showResults()
                productsAdapter.setItems(resource.data ?: emptyList())
            }
            LOADING -> {
                showLoading()
            }
            ERROR -> {
                showError()
            }
            NETWORK_DISCONNECTED -> {
                showError()
                binding?.let {
                    Snackbar.make(it.root, com.choco.core.R.string.error_no_connection, Snackbar.LENGTH_SHORT).show()
                }
            }
            else -> {}
        }
        updateOrderButtonVisibility(resource?.data)
    }

    private val createOrderObserver = Observer<Resource<String>> { resource ->
        when(resource?.status) {
            SUCCESS -> {
                binding?.apply {
                    Snackbar.make(root, getString(R.string.order_placed, resource.data) , Snackbar.LENGTH_SHORT).show()
                }
            }

            ERROR -> {
                binding?.apply {
                    Snackbar.make(root, R.string.order_failed, Snackbar.LENGTH_SHORT).show()
                }
            }
            else -> {

            }
        }
    }

    private fun showResults() {
        binding?.apply {
            recyclerView.show()
            loadingLayout.root.hide()
            groupError.hide()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun showLoading() {
        binding?.apply {
            if (productsAdapter.itemCount == 0) {
                recyclerView.hide()
                loadingLayout.root.show()
            }
            groupError.hide()
        }
    }

    private fun showError() {
        binding?.apply {
            if (productsAdapter.itemCount == 0)
                recyclerView.hide()
            loadingLayout.root.hide()
            groupError.show()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private val onProductQuantityChangedListener = { product: Product, quantity: Int ->
        viewModel.updateQuantityForProductInState(product, quantity)
    }

    private fun updateOrderButtonVisibility(entries: List<Entry>?) {
        val productToBuy = viewModel.productsLiveData.value?.data?.firstOrNull { productEntry ->
            productEntry.quantity > 0
        }
        binding?.apply {
        if (productToBuy != null)
            buttonPlaceOrder.show()
        else
            buttonPlaceOrder.hide()
        }
    }
}