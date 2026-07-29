package com.singular.manager.domain.usecase

import com.singular.manager.domain.model.Game
import com.singular.manager.domain.repository.GameRepository

class GetGameByIdUseCase(private val repository: GameRepository) {
    suspend operator fun invoke(gameId: Int): Game? {
        return repository.getGameById(gameId)
    }
}
