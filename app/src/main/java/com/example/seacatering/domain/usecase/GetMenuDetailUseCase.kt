package com.example.seacatering.domain.usecase

import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.repository.MenuRepository

class GetMenuDetailUseCase(private val repo: MenuRepository) {
    suspend operator fun invoke(menuId: String) : Status {
        return  repo.getMenuDetail(menuId)
    }
}