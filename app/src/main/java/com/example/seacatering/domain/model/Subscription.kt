package com.example.seacatering.domain.model

import com.google.firebase.Timestamp

data class Subscription(
    val id: String = "",
    val user_id: String = "",
    val plan_id: String = "",
    val name: String = "",
    val phone_number: String = "",
    val meal_type: String = "",
    val delivery_days: String = "",
    val alergies: String = "",
    val status: String = "",
    val total_price: Double = 0.0,
    val end_date: String = "",
    val pause_period_start: String = "",
    val pause_period_end: String = "",
)
