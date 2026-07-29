package com.singular.manager.domain.model

data class Game(
    val id: Int = 0,
    val name: String,
    val packageName: String,
    val eventTokens: String?
)
