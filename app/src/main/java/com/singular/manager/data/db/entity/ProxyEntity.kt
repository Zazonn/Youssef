package com.singular.manager.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "proxies")
data class ProxyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val host: String,
    val port: Int,
    val username: String?,
    val password: String?,
    val isActive: Boolean = true
)
