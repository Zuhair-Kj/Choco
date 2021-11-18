package com.choco.browse.di

import com.choco.browse.cache.OrderDao
import com.choco.browse.interactors.DeleteAllOrdersUseCase
import com.choco.browse.interactors.CreateOrderUseCase
import com.choco.browse.interactors.GetAllOrdersUseCase
import com.choco.browse.interactors.GetAllProductsUseCase
import com.choco.browse.network.ProductsApiService
import com.choco.browse.repo.OrdersRepository
import com.choco.browse.repo.ProductsRepository
import com.choco.browse.viewmodel.OrdersViewModel
import com.choco.browse.viewmodel.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

@JvmField
val browseModule = module {
    // ============= ViewModels =============
    viewModel(named("ProductsFragment")) {
        ProductsViewModel(
            getAllProductsUseCase = get(),
            createOrderUseCase = get(),
            networkHelper = get(),
            userSessionManager = get(),
            dispatchersProvider = get()
        )
    }

    viewModel {
        OrdersViewModel(ordersRepository = get(), dispatchersProvider = get())
    }
    // ============= Repos =============
    single {
        ProductsRepository(productsApiService = get())
    }
    single {
        OrdersRepository(ordersDao = get())
    }
    // ============= DAOs and api services =============

    single { get<OrderDao>() }
    single { get<Retrofit>().create(ProductsApiService::class.java) }

    // ============= Interact =============

    single { GetAllOrdersUseCase(ordersRepository = get()) }
    single { CreateOrderUseCase(ordersRepository = get()) }
    single { DeleteAllOrdersUseCase(ordersRepository = get()) }
    single { GetAllProductsUseCase(productsRepository = get()) }
}