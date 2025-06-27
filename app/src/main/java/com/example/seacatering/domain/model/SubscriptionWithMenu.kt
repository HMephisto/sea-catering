package com.example.seacatering.domain.model

import com.google.firebase.Timestamp

data class SubscriptionWithMenu(
    val subscription: Subscription,
    val menu: Menu
)