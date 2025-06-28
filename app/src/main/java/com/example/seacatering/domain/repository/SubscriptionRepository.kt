package com.example.seacatering.domain.repository

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.Subscription
import com.google.firebase.Timestamp

interface SubscriptionRepository {
    suspend fun createSubscription(subscription: Subscription): Status
    suspend fun getSubscriptions(userId: String): Status
    suspend fun togglePauseSubscription(subscriptionId: String, startDate: Timestamp, endDate: Timestamp, pause: Boolean) : Status
    suspend fun cancelSubscription(subscriptionId: String, endDate: String) : Status
    suspend fun getAllSubscriptions(status: String): Status
    suspend fun getNewSubscriptionsData(startDate: Timestamp, endDate: Timestamp) : Status
    suspend fun getReactivationData(startDate: Timestamp, endDate: Timestamp) : Status
    suspend fun getActiveSubscriptions() : Status
}