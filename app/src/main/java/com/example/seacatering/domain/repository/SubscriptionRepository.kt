package com.example.seacatering.domain.repository

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.Subscription

interface SubscriptionRepository {
    suspend fun createSubscription(subscription: Subscription): Status
    suspend fun getSubscriptions(userId: String): Status
    suspend fun togglePauseSubscription(subscriptionId: String, startDate: String, endDate: String, pause: Boolean) : Status
    suspend fun cancelSubscription(subscriptionId: String, endDate: String) : Status
}