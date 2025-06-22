package com.example.seacatering.data.repository

import com.example.seacatering.data.firestore.FirestoreService
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.repository.MenuRepository

class MenuRepositoryImpl(private val firestoreService: FirestoreService) : MenuRepository {
    override suspend fun getAllMenu(): Status = firestoreService.getAllMenu()
    override suspend fun getMenuDetail(menuId: String): Status = firestoreService.getMenuDetail(menuId)
}