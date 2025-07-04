package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.repository.AuthRepository

class RegisterUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Status {
        return repo.register(email, password)
    }
}