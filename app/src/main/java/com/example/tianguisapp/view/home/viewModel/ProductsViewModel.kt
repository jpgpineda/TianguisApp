package com.example.tianguisapp.view.home.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tianguisapp.core.ResultWrapper
import com.example.tianguisapp.model.Product
import com.example.tianguisapp.network.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: StoreRepository
): ViewModel() {
    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean>
        get() = _loaderState
    private val _productInfo = MutableLiveData<List<Product>>()
    val productInfo: LiveData<List<Product>>
        get() = _productInfo

    fun fetchAllProducts() {
        _loaderState.value = true
        viewModelScope.launch {
            when (val result = repository.fetchAllProducts()) {
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