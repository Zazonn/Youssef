package com.singular.manager.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val gaid: String?,
    val email: String?,
    val uid: String?,
    val createdAt: Long = System.currentTimeMillis()
)
