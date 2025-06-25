package com.example.seacatering.domain.repository

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.Subscription

interface SubscriptionRepository {
    suspend fun createSubscription(subscription: Subscription): Status
}