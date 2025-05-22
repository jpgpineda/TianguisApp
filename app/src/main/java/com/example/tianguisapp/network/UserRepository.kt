package com.example.tianguisapp.network

import com.example.tianguisapp.core.ResultWrapper
import com.example.tianguisapp.core.safeCall
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun login(email: String, password: String): ResultWrapper<FirebaseUser> = safeCall {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        result.user ?: throw Exception("usuario no encontrado")
    }
}