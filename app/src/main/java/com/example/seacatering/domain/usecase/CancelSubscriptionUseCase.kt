package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.repository.SubscriptionRepository

class CancelSubscriptionUseCase (private val repo: SubscriptionRepository) {
    suspend operator fun invoke(subscriptionId: String, endDate: String) : Status {
        return repo.cancelSubscription(subscriptionId, endDate)
    }
}