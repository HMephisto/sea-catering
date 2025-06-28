package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.repository.SubscriptionRepository
import com.google.firebase.Timestamp

class GetReactivationUseCase (private val repo: SubscriptionRepository) {
    suspend operator fun invoke(startDate: Timestamp, endDate: Timestamp) : Status {
        return repo.getReactivationData(startDate, endDate)
    }
}
