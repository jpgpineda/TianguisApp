package com.example.tianguisapp.network

import android.util.Log
import com.example.tianguisapp.core.ResultWrapper
import com.example.tianguisapp.core.RetrofitInstance
import com.example.tianguisapp.core.StoreAPI
import com.example.tianguisapp.core.safeCall
import com.example.tianguisapp.model.Product
import retrofit2.HttpException
import retrofit2.Retrofit
import javax.inject.Inject

class StoreRepository @Inject constructor(
    private val storeAPI: StoreAPI
) {

    suspend fun getProductDetail(id: String): ResultWrapper<Product> = safeCall {
        val response = storeAPI.getProductDetail(id)
        Log.i("RESPONSE", response.body().toString())
        if (response.isSuccessful) {
            response.body() ?: throw Exception("Datos nulos")
        } else {
            throw HttpException(response)
        }
    }

    suspend fun fetchAllProducts(): ResultWrapper<List<Product>> = safeCall {
        val response = storeAPI.fetchAllProducts()
        Log.i("Response", response.body().toString())
        if (response.isSuccessful) {
            response.body() ?: throw Exception("Datos nulos")
        } else {
            throw HttpException(response)
        }
    }
}