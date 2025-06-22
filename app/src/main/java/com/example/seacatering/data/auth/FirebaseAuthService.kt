package com.example.seacatering.data.auth

import com.example.seacatering.domain.model.Status
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuthService(private val firebaseAuth: FirebaseAuth) {
    suspend fun signIn(email: String, password: String): Status =
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Status.Success
        } catch (e: Exception){
            Status.Failure(e.message ?: "Login Failed")
        }

    suspend fun register(email: String, password: String): Status =
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Status.Success
        } catch (e: Exception){
            Status.Failure(e.message ?: "Register Failed")
        }

}