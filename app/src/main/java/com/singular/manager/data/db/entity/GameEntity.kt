package com.singular.manager.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val packageName: String,
    val eventTokens: String? // Comma-separated event tokens
)
