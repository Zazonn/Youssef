package com.singular.manager.domain.usecase

import com.singular.manager.domain.model.Game
import com.singular.manager.domain.repository.GameRepository

class DeleteGameUseCase(private val repository: GameRepository) {
    suspend operator fun invoke(game: Game) {
        repository.deleteGame(game)
    }
}
