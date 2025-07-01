package com.example.seacatering.data.repository

import com.example.seacatering.data.firestore.FirestoreService
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.Testimony
import com.example.seacatering.domain.model.User
import com.example.seacatering.domain.repository.UserRepository

class UserRepositortImpl(private val firestoreService: FirestoreService) : UserRepository {
    override suspend fun getUserData(userId: String): Status = firestoreService.getUserData(userId)
    override suspend fun createUser(user: User): Status = firestoreService.createUser(user)
    override suspend fun createTestimony(testimony: Testimony): Status = firestoreService.addTestimony(testimony)
    override suspend fun getTestimonials(): Status = firestoreService.getTestimony()
    override suspend fun getUserTestimony(userId : String): Status = firestoreService.getUserTestimony(userId)

}