package com.example.tianguisapp.network

import android.util.Log
import com.example.tianguisapp.core.RetrofitInstance
import com.example.tianguisapp.core.StoreAPI
import com.example.tianguisapp.model.Product

class StoreRepository {
    private val retrofit = RetrofitInstance.getRetrofit().create(StoreAPI::class.java)

    suspend fun getProductDetail(): Product? {
        val response = retrofit.getProductDetail()
        Log.i("RESPONSE", response.body().toString())

        return response.body()
    }
}