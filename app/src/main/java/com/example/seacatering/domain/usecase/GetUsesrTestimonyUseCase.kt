package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.repository.UserRepository

class GetUserTestimonyUseCase (private val repo: UserRepository) {
    suspend operator fun invoke(userId : String): Status {
        return repo.getUserTestimony(userId)
    }
}