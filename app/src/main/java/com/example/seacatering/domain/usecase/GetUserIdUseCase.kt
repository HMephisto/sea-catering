package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetUserIdUseCase(private val repo: UserPreferencesRepository) {
    operator fun invoke(): Flow<String?> = repo.getUserId()
}