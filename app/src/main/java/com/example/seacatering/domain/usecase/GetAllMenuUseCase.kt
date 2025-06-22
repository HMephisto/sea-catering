package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.repository.MenuRepository

class GetAllMenuUseCase(private val repo: MenuRepository) {
    suspend operator fun invoke(): Status {
        return repo.getAllMenu()
    }
}