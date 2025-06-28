package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.repository.SubscriptionRepository

class GetActiveSubscriptionsUseCase (private val repo: SubscriptionRepository) {
    suspend operator fun invoke() : Status {
        return repo.getActiveSubscriptions()
    }
}