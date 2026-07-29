package com.singular.manager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.singular.manager.data.db.dao.GameDao
import com.singular.manager.data.db.dao.ProfileDao
import com.singular.manager.data.db.dao.ProxyDao
import com.singular.manager.data.db.entity.GameEntity
import com.singular.manager.data.db.entity.ProfileEntity
import com.singular.manager.data.db.entity.ProxyEntity

@Database(entities = [ProfileEntity::class, GameEntity::class, ProxyEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun gameDao(): GameDao
    abstract fun proxyDao(): ProxyDao
}
