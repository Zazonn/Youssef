package com.singular.manager.domain.usecase

import com.singular.manager.domain.model.Game
import com.singular.manager.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

class GetGamesUseCase(private val repository: GameRepository) {
    operator fun invoke(): Flow<List<Game>> {
        return repository.getAllGames()
    }
}
