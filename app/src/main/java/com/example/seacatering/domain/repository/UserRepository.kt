package com.example.seacatering.domain.repository

import com.example.seacatering.domain.model.AuthResult
import com.example.seacatering.domain.model.User

interface UserRepository {
//    suspend fun getUser(id: String): User
    suspend fun createUser(user: User): AuthResult
}