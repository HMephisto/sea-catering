package com.example.seacatering.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    suspend fun saveUserId(userId:String)
    fun getUserId(): Flow<String?>
}