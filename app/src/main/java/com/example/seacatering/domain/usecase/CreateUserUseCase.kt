package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.User
import com.example.seacatering.domain.repository.UserRepository

class CreateUserUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(user: User): Status {
        return repo.createUser(user)
    }
}