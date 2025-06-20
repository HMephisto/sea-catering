package com.example.seacatering.data.auth

import com.example.seacatering.domain.model.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuthService(private val firebaseAuth: FirebaseAuth) {
    suspend fun signIn(email: String, password: String): AuthResult =
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success
        } catch (e: Exception){
            AuthResult.Failure(e.message ?: "Login Failed")
        }

    suspend fun register(email: String, password: String): AuthResult =
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            AuthResult.Success
        } catch (e: Exception){
            AuthResult.Failure(e.message ?: "Register Failed")
        }

}