package com.example.seacatering.domain.repository

import com.example.seacatering.domain.model.Status

interface AuthRepository {
    suspend fun login(email: String, password: String): Status
    suspend fun register(email: String, password: String): Status
    fun logout()
    fun getCurrentUserId(): String?
}