package com.choco.browse.network

import com.choco.browse.model.Product
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApiService {
    @GET("/choco/products")
    suspend fun getProducts(@Query("token") token: String): List<Product>
}