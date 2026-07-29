package com.singular.manager.domain.model

data class Proxy(
    val id: Int = 0,
    val host: String,
    val port: Int,
    val username: String?,
    val password: String?,
    val isActive: Boolean = true
)
