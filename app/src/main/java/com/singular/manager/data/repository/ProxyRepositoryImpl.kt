package com.singular.manager.data.repository

import com.singular.manager.data.db.dao.ProxyDao
import com.singular.manager.data.db.entity.ProxyEntity
import com.singular.manager.domain.model.Proxy
import com.singular.manager.domain.repository.ProxyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProxyRepositoryImpl(private val proxyDao: ProxyDao) : ProxyRepository {

    override fun getAllProxies(): Flow<List<Proxy>> {
        return proxyDao.getAllProxies().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getProxyById(proxyId: Int): Proxy? {
        return proxyDao.getProxyById(proxyId)?.toDomain()
    }

    override suspend fun insertProxy(proxy: Proxy) {
        proxyDao.insertProxy(proxy.toEntity())
    }

    override suspend fun updateProxy(proxy: Proxy) {
        proxyDao.updateProxy(proxy.toEntity())
    }

    override suspend fun deleteProxy(proxy: Proxy) {
        proxyDao.deleteProxy(proxy.toEntity())
    }

    private fun ProxyEntity.toDomain(): Proxy {
        return Proxy(id, host, port, username, password, isActive)
    }

    private fun Proxy.toEntity(): ProxyEntity {
        return ProxyEntity(id, host, port, username, password, isActive)
    }
}
