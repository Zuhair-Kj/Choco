package com.choco.mobile.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.choco.R
import com.choco.browse.model.Order
import com.choco.browse.view.fragment.OrderDetailsFragment
import com.choco.browse.view.fragment.OrdersListFragment
import com.choco.core_ui.BaseFragment
import com.choco.databinding.FragmentMainBinding
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.bind
import org.koin.dsl.module

class MainFragment : BaseFragment<FragmentMainBinding>() {
    override fun getLayoutId() = R.layout.fragment_main
    private val navController by lazy {
        (childFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        unloadKoinModules(extraModule)
        loadKoinModules(extraModule)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.bottomNav?.setupWithNavController(navController)
    }

    private val extraModule = module {
        single {
            ordersBridge
        } bind OrdersListFragment.Bridge::class
    }
    private val ordersBridge = object : OrdersListFragment.Bridge {
        override fun openOrderDetailsPage(order: Order) {
            findNavController().navigate(
                R.id.action_mainFragment_to_orderDetailsFragment,
                bundleOf(Pair(OrderDetailsFragment.KEY_ORDER, order))
            )
        }

    }

}