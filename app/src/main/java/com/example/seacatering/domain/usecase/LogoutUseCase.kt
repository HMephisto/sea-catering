package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.repository.AuthRepository

class LogoutUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(): Status {
        return repo.logout()
    }
}