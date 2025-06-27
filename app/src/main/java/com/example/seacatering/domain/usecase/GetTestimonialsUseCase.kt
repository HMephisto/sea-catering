package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.Testimony
import com.example.seacatering.domain.repository.UserRepository

class GetTestimonialsUseCase (private val repo: UserRepository) {
    suspend operator fun invoke(): Status {
        return repo.getTestimonials()
    }
}