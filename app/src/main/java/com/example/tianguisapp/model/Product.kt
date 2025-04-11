package com.example.tianguisapp.model

import com.google.gson.annotations.SerializedName

data class Product(
    val id: String,
    val title: String,
    val price: Double,
    @SerializedName("description") val description: String,
    val category: String,
    val image: String
)
