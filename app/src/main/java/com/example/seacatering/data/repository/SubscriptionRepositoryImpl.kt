package com.example.seacatering.data.repository

import com.example.seacatering.data.firestore.FirestoreService
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.Subscription
import com.example.seacatering.domain.repository.SubscriptionRepository

class SubscriptionRepositoryImpl(private val firestoreService: FirestoreService) : SubscriptionRepository {
    override suspend fun createSubscription(subscription: Subscription): Status = firestoreService.createSubscription(subscription)
}