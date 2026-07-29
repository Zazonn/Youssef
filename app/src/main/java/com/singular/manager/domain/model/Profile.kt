package com.singular.manager.domain.model

data class Profile(
    val id: Int = 0,
    val name: String,
    val gaid: String?,
    val email: String?,
    val uid: String?,
    val createdAt: Long
)
