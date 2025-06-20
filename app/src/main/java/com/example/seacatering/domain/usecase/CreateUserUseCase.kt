package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.model.AuthResult
import com.example.seacatering.domain.model.User
import com.example.seacatering.domain.repository.UserRepository

class CreateUserUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(user: User): AuthResult {
        return repo.createUser(user)
    }
}