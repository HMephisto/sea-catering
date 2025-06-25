package com.example.seacatering.data.repository

import com.example.seacatering.data.local.DataStoreManager
import com.example.seacatering.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPreferencesRepositoryImpl(private val dataStoreManager: DataStoreManager)
    : UserPreferencesRepository{
    override suspend fun saveUserId(userId: String) {
        dataStoreManager.saveUserId(userId)
    }

    override fun getUserId(): Flow<String?> {
        return dataStoreManager.getUserId
    }

}