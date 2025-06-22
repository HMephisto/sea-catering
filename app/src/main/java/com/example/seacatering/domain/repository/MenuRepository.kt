package com.example.seacatering.domain.repository

import com.example.seacatering.domain.model.Status

interface MenuRepository {
    suspend fun getAllMenu() : Status
    suspend fun getMenuDetail(menuId: String): Status
}