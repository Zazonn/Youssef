package com.singular.manager.domain.repository

import com.singular.manager.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getAllGames(): Flow<List<Game>>
    suspend fun getGameById(gameId: Int): Game?
    suspend fun insertGame(game: Game)
    suspend fun updateGame(game: Game)
    suspend fun deleteGame(game: Game)
    fun getGamesWithoutEventTokens(): Flow<List<Game>>
}
