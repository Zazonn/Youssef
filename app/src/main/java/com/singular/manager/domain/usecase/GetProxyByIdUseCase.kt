package com.singular.manager.domain.usecase

import com.singular.manager.domain.model.Proxy
import com.singular.manager.domain.repository.ProxyRepository

class GetProxyByIdUseCase(private val repository: ProxyRepository) {
    suspend operator fun invoke(proxyId: Int): Proxy? {
        return repository.getProxyById(proxyId)
    }
}
