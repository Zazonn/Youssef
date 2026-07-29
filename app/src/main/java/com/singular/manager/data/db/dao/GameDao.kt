package com.singular.manager.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.singular.manager.data.db.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM games ORDER BY name ASC")
    fun getAllGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM games WHERE id = :gameId")
    suspend fun getGameById(gameId: Int): GameEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameEntity)

    @Update
    suspend fun updateGame(game: GameEntity)

    @Delete
    suspend fun deleteGame(game: GameEntity)

    @Query("SELECT * FROM games WHERE eventTokens IS NULL OR eventTokens = ''")
    fun getGamesWithoutEventTokens(): Flow<List<GameEntity>>
}
