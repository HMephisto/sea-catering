package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.repository.SubscriptionRepository
import com.google.firebase.Timestamp

class TogglePauseSubscriptionUseCase (private val repo: SubscriptionRepository) {
    suspend operator fun invoke(subscriptionId: String, startDate: Timestamp, endDate: Timestamp, pause: Boolean) : Status {
        return repo.togglePauseSubscription(subscriptionId, startDate, endDate,pause)
    }
}