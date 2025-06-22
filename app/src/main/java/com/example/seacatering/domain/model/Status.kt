package com.example.seacatering.domain.model

sealed class Status {
    object Success : Status()
    data class SuccessWithData<T>(val data: T) : Status()
    data class Failure(val message: String) : Status()
    object Loading : Status()

}