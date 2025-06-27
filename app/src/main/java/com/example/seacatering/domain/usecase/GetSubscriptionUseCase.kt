package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.repository.SubscriptionRepository

class GetSubscriptionUseCase(private val repo: SubscriptionRepository) {
    suspend operator fun invoke(userId: String) : Status {
        return repo.getSubscriptions(userId)
    }
}