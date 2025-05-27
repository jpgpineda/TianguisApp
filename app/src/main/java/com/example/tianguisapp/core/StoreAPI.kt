package com.example.tianguisapp.core

import com.example.tianguisapp.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreAPI {
    @GET("products/{id}")
    suspend fun getProductDetail(@Path("id") id: String):Response<Product>

    @GET("products")
    suspend fun fetchAllProducts(): Response<List<Product>>
}