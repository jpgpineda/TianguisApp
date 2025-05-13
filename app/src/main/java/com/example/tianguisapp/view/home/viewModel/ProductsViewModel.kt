package com.example.tianguisapp.view.home.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tianguisapp.model.Product
import com.example.tianguisapp.network.StoreRepository
import kotlinx.coroutines.launch

class ProductsViewModel: ViewModel() {
    private val repository = StoreRepository()
    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean>
        get() = _loaderState
    private val _productInfo = MutableLiveData<List<Product>>()
    val productInfo: LiveData<List<Product>>
        get() = _productInfo

    fun fetchAllProducts() {
        _loaderState.value = true
        viewModelScope.launch {
            val response = repository.fetchAllProducts()
            _loaderState.value = false
            response?.let {
                _productInfo.value = it
            } ?: run {
                Log.e("API ERROR", "NO SE PUDO COMPLETAR LA PETICION")
            }
        }
    }
}