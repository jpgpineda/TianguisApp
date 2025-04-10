package com.example.tianguisapp.core

import com.example.tianguisapp.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface StoreAPI {
    @GET("products/1")
    suspend fun getProductDetail():Response<Product>
}