package com.example.seacatering.data.repository

import com.example.seacatering.data.firestore.FirestoreService
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.Subscription
import com.example.seacatering.domain.repository.SubscriptionRepository

class SubscriptionRepositoryImpl(private val firestoreService: FirestoreService) : SubscriptionRepository {
    override suspend fun createSubscription(subscription: Subscription): Status = firestoreService.createSubscription(subscription)
    override suspend fun getSubscriptions(userId: String): Status = firestoreService.getSubscription(userId)
    override suspend fun togglePauseSubscription(
        subscriptionId: String,
        startDate: String,
        endDate: String,
        pause: Boolean
    ): Status = firestoreService.togglePauseSubscription(subscriptionId, startDate , endDate,pause)

    override suspend fun cancelSubscription(subscriptionId: String, endDate: String): Status = firestoreService.cancelSubscription(subscriptionId, endDate)
}