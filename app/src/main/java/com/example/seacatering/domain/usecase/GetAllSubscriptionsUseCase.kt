package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.repository.SubscriptionRepository

class GetAllSubscriptionsUseCase (private val repo: SubscriptionRepository) {
    suspend operator fun invoke(status: String) : Status {
        return repo.getAllSubscriptions(status)
    }
}