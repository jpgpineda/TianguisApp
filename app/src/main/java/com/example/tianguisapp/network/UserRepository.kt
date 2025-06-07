package com.example.tianguisapp.network

import com.example.tianguisapp.core.ResultWrapper
import com.example.tianguisapp.core.safeCall
import com.example.tianguisapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
){
    private val userCollection = firestore.collection("Users")

    suspend fun login(email: String, password: String): ResultWrapper<FirebaseUser> = safeCall {
        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        result.user ?: throw Exception("usuario no encontrado")
    }

    suspend fun requestSignUp(email: String, password: String): ResultWrapper<FirebaseUser> = safeCall {
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        result.user ?: throw Exception("No se pudo crear el usuario")
    }

    suspend fun createUser(user: User): ResultWrapper<Void> = safeCall {
        userCollection.document(user.id).set(user).await()
    }

    suspend fun getUser(): ResultWrapper<User> = safeCall {
        firebaseAuth.currentUser?.uid.let { id ->
            val snapShot = id?.let { it1 -> userCollection.document(it1).get().await() }
            snapShot?.toObject(User::class.java) ?: throw Exception("usuario no encontrado")
        }
    }

    suspend fun updateUser(user: User): ResultWrapper<Void> = safeCall {
        userCollection.document(user.id).set(user).await()
    }

    suspend fun deleteUser(id: String): ResultWrapper<Void> = safeCall {
        userCollection.document(id).delete().await()
    }
}