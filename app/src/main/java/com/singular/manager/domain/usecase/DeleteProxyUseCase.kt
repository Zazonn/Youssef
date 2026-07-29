package com.singular.manager.domain.usecase

import com.singular.manager.domain.model.Proxy
import com.singular.manager.domain.repository.ProxyRepository

class DeleteProxyUseCase(private val repository: ProxyRepository) {
    suspend operator fun invoke(proxy: Proxy) {
        repository.deleteProxy(proxy)
    }
}
