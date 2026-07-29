package com.singular.manager.domain.repository

import com.singular.manager.domain.model.Proxy
import kotlinx.coroutines.flow.Flow

interface ProxyRepository {
    fun getAllProxies(): Flow<List<Proxy>>
    suspend fun getProxyById(proxyId: Int): Proxy?
    suspend fun insertProxy(proxy: Proxy)
    suspend fun updateProxy(proxy: Proxy)
    suspend fun deleteProxy(proxy: Proxy)
}
