package com.example.tianguisapp.network

import android.util.Log
import com.example.tianguisapp.core.ResultWrapper
import com.example.tianguisapp.core.RetrofitInstance
import com.example.tianguisapp.core.StoreAPI
import com.example.tianguisapp.core.safeCall
import com.example.tianguisapp.model.Product
import retrofit2.HttpException

class StoreRepository {
    private val retrofit = RetrofitInstance.getRetrofit().create(StoreAPI::class.java)

    suspend fun getProductDetail(): ResultWrapper<Product> = safeCall {
        val response = retrofit.getProductDetail()
        Log.i("RESPONSE", response.body().toString())
        if (response.isSuccessful) {
            response.body() ?: throw Exception("Datos nulos")
        } else {
            throw HttpException(response)
        }
    }

    suspend fun fetchAllProducts(): ResultWrapper<List<Product>> = safeCall {
        val response = retrofit.fetchAllProducts()
        Log.i("Response", response.body().toString())
        if (response.isSuccessful) {
            response.body() ?: throw Exception("Datos nulos")
        } else {
            throw HttpException(response)
        }
    }
}