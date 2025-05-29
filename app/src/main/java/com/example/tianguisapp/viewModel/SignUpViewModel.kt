package com.example.tianguisapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tianguisapp.core.ResultWrapper
import com.example.tianguisapp.network.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {
    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean>
        get() = _loaderState
    private val _isUserCreted = MutableLiveData<String>()
    val isUserCreated: LiveData<String>
        get() = _isUserCreted

    fun requestSignUp(email: String, password: String) {
        _loaderState.value = true
        viewModelScope.launch {
            when (val result = repository.requestSignUp(email, password)) {
                is ResultWrapper.Success -> {
                    _loaderState.value = false
                    _isUserCreted.value = result.data.email
                }
                is ResultWrapper.Error -> {
                    _loaderState.value = false
                    Log.e("Firebase", "Ocurrio un problema")
                }
            }
        }
    }
}