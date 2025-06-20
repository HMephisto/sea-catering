package com.example.seacatering.domain.model

sealed class AuthResult {
    object Success : AuthResult()
    data class Failure(val message: String) : AuthResult()
    object Loading : AuthResult()

}