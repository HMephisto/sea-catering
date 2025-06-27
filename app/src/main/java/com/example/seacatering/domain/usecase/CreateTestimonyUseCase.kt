package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.Testimony
import com.example.seacatering.domain.repository.UserRepository

class CreateTestimonyUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(testimony: Testimony): Status {
        return repo.createTestimony(testimony)
    }
}