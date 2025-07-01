package com.example.seacatering.domain.model

data class Menu(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val image_url: String = "",
)
