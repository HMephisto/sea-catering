package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class SaveUserIdUseCase(private val repo: UserPreferencesRepository) {
    suspend operator fun invoke(userId: String) = repo.saveUserId(userId)
}