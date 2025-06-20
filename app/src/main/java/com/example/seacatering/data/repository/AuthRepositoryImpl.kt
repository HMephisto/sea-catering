package com.example.seacatering.data.repository

import com.example.seacatering.data.auth.FirebaseAuthService
import com.example.seacatering.domain.model.AuthResult
import com.example.seacatering.domain.repository.AuthRepository

class AuthRepositoryImpl(private val authService: FirebaseAuthService) : AuthRepository {
    override suspend fun login(email: String, password: String): AuthResult =
        authService.signIn(email, password)


    override suspend fun register(
        email: String,
        password: String
    ): AuthResult = authService.register(email, password)


    override fun logout() {
        TODO("Not yet implemented")
    }

    override fun getCurrentUserId(): String? {
        TODO("Not yet implemented")
    }
}