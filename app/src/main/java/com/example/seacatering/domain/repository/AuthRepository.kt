package com.example.seacatering.domain.repository

import com.example.seacatering.domain.model.AuthResult

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult
    suspend fun register(email: String, password: String): AuthResult
    fun logout()
    fun getCurrentUserId(): String?
}