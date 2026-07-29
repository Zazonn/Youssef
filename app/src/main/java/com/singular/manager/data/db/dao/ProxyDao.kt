package com.singular.manager.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.singular.manager.data.db.entity.ProxyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProxyDao {
    @Query("SELECT * FROM proxies ORDER BY id DESC")
    fun getAllProxies(): Flow<List<ProxyEntity>>

    @Query("SELECT * FROM proxies WHERE id = :proxyId")
    suspend fun getProxyById(proxyId: Int): ProxyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProxy(proxy: ProxyEntity)

    @Update
    suspend fun updateProxy(proxy: ProxyEntity)

    @Delete
    suspend fun deleteProxy(proxy: ProxyEntity)
}
