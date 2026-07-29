package com.singular.manager.domain.usecase

import com.singular.manager.domain.model.Profile
import com.singular.manager.root.RootDataManager

class BuildProfileFromDeviceUseCase(private val rootDataManager: RootDataManager) {
    suspend operator fun invoke(): Profile? {
        return rootDataManager.buildProfileFromDevice()
    }
}
