package com.singular.manager.data.repository

import com.singular.manager.data.db.dao.GameDao
import com.singular.manager.data.db.entity.GameEntity
import com.singular.manager.domain.model.Game
import com.singular.manager.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameRepositoryImpl(private val gameDao: GameDao) : GameRepository {

    override fun getAllGames(): Flow<List<Game>> {
        return gameDao.getAllGames().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getGameById(gameId: Int): Game? {
        return gameDao.getGameById(gameId)?.toDomain()
    }

    override suspend fun insertGame(game: Game) {
        gameDao.insertGame(game.toEntity())
    }

    override suspend fun updateGame(game: Game) {
        gameDao.updateGame(game.toEntity())
    }

    override suspend fun deleteGame(game: Game) {
        gameDao.deleteGame(game.toEntity())
    }

    override fun getGamesWithoutEventTokens(): Flow<List<Game>> {
        return gameDao.getGamesWithoutEventTokens().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    private fun GameEntity.toDomain(): Game {
        return Game(id, name, packageName, eventTokens)
    }

    private fun Game.toEntity(): GameEntity {
        return GameEntity(id, name, packageName, eventTokens)
    }
}
