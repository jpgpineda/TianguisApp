package com.example.tianguisapp.view.home.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tianguisapp.core.ResultWrapper
import com.example.tianguisapp.model.Product
import com.example.tianguisapp.network.StoreRepository
import kotlinx.coroutines.launch

class ProductDetailViewModel: ViewModel() {
    private val repository = StoreRepository()
    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean>
        get() = _loaderState
    private val _productInfo = MutableLiveData<Product>()
    val productInfo: LiveData<Product>
        get() = _productInfo

    fun getProductDetail() {
        _loaderState.value = true
        viewModelScope.launch {
            when (val result = repository.getProductDetail()) {
                is ResultWrapper.Success -> {
                    _loaderState.value = false
                    _productInfo.value = result.data
                }
                is ResultWrapper.Error -> {
                    _loaderState.value = false
                    val errorMessage = result.exception.message
                }
            }
        }
    }

}