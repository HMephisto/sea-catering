package com.example.seacatering.data.firestore

import com.example.seacatering.domain.model.AuthResult
import com.example.seacatering.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreService(private val db: FirebaseFirestore) {
    suspend fun createUser(user: User): AuthResult =
        try {
            db.collection("users").document(user.id).set(user).await()
            AuthResult.Success
        } catch (e: Exception){
            AuthResult.Failure(e.message ?: "Failed to Create User")
        }
    }
