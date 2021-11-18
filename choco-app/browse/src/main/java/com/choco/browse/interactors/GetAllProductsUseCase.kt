package com.choco.browse.interactors

import com.choco.browse.repo.ProductsRepository

class GetAllProductsUseCase(private val productsRepository: ProductsRepository) {
    suspend fun getAllProducts(token: String) = productsRepository.getProducts(token)
}