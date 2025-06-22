package com.example.seacatering.data.firestore

import com.example.seacatering.domain.model.Menu
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreService(private val db: FirebaseFirestore) {
    suspend fun createUser(user: User): Status =
        try {
            db.collection("users").document(user.id).set(user).await()
            Status.Success
        } catch (e: Exception){
            Status.Failure(e.message ?: "Failed to Create User")
        }

    suspend fun getAllMenu(): Status =
        try {
            val result = db.collection("menus").get().await()
            val menus = result.toObjects(Menu::class.java)
            Status.SuccessWithData(menus)
        } catch (e: Exception) {
            Status.Failure(e.message ?: "Failed to get Menu Data")
        }

    suspend fun getMenuDetail(menuId : String): Status =
        try {
            val result = db.collection("menus").document(menuId).get().await()
            val menu = result.toObject(Menu::class.java)
            Status.SuccessWithData(menu)
        } catch (e: Exception) {
            Status.Failure(e.message ?: "Failed to get Menu Data")
        }
    }
