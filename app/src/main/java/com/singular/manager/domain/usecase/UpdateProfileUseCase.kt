package com.singular.manager.domain.usecase

import com.singular.manager.domain.model.Profile
import com.singular.manager.domain.repository.ProfileRepository

class UpdateProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(profile: Profile) {
        repository.updateProfile(profile)
    }
}
