package com.choco.browse.repo

import com.choco.browse.model.Product
import com.choco.browse.network.ProductsApiService

class ProductsRepository(private val productsApiService: ProductsApiService) {
    suspend fun getProducts(token: String?): List<Product> {
        if (token == null)
            throw IllegalArgumentException("Token should not be null")
        return productsApiService.getProducts(token)
    }
}