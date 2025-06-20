package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.model.AuthResult
import com.example.seacatering.domain.repository.AuthRepository

class LoginUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): AuthResult {
        return repo.login(email, password)
    }
}