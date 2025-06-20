package com.example.seacatering.data.repository

import com.example.seacatering.data.firestore.FirestoreService
import com.example.seacatering.domain.model.AuthResult
import com.example.seacatering.domain.model.User
import com.example.seacatering.domain.repository.UserRepository

class UserRepositortImpl(private val firestoreService: FirestoreService) : UserRepository {
    override suspend fun createUser(user: User): AuthResult = firestoreService.createUser(user)
}