package com.choco.mobile.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.choco.R
import com.choco.browse.repo.OrdersRepository
import com.choco.core.auth.UserSessionManager
import com.choco.core.util.MainBridge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class MainActivity: AppCompatActivity(), MainBridge {
    val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }
    private val userSessionManager: UserSessionManager by inject()
    private val ordersRepository: OrdersRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onBackPressedDispatcher.addCallback(this, pressBackCallback)
    }

    override fun actionLoginToMain() {
        if (navController.currentDestination?.id == R.id.loginFragment)
            navController.navigate(R.id.action_loginFragment_to_mainFragment)
    }

    override fun logout() {
        val navBuilder = NavOptions.Builder()
        navController.currentDestination?.id?.let {
                navBuilder.setPopUpTo(it, true)
        }
        // clear the ProductsViewModel tied to this activity viewModel Store to clear its data.
        viewModelStore.clear()
        savedStateRegistry.unregisterSavedStateProvider("ProductsFragment")
        lifecycleScope.launch(Dispatchers.IO) {
            userSessionManager.clearSession()
            ordersRepository.clearOrders()
            withContext(Dispatchers.Main) {
                navController.navigate(R.id.action_global_logout, null, navBuilder.build())
            }
        }
    }

    private val pressBackCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!navController.popBackStack()) {
                    finish()
                }
            }
        }
    }
}