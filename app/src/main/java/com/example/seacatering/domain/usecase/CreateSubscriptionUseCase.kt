package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.Subscription
import com.example.seacatering.domain.repository.SubscriptionRepository

class CreateSubscriptionUseCase(private val repo: SubscriptionRepository) {
    suspend operator fun invoke(subscription: Subscription): Status {
        return repo.createSubscription(subscription)
    }
}