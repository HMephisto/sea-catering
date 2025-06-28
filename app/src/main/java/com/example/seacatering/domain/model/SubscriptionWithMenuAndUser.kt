package com.example.seacatering.domain.model

import com.google.firebase.Timestamp

data class SubscriptionWithMenuAndUser(
    val subscription: Subscription,
    val menu: Menu,
    val user: User
)