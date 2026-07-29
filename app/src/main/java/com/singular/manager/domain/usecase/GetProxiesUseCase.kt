package com.singular.manager.domain.usecase

import com.singular.manager.domain.model.Proxy
import com.singular.manager.domain.repository.ProxyRepository
import kotlinx.coroutines.flow.Flow

class GetProxiesUseCase(private val repository: ProxyRepository) {
    operator fun invoke(): Flow<List<Proxy>> {
        return repository.getAllProxies()
    }
}
