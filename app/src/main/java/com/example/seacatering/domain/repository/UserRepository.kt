package com.example.seacatering.domain.repository

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.Testimony
import com.example.seacatering.domain.model.User

interface UserRepository {
    suspend fun getUserData(userId: String): Status
    suspend fun createUser(user: User): Status
    suspend fun createTestimony(testimony: Testimony) : Status
    suspend fun getTestimonials(): Status
    suspend fun getUserTestimony(userId: String): Status
}