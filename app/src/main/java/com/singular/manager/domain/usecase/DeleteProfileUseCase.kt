package com.singular.manager.domain.usecase

import com.singular.manager.domain.model.Profile
import com.singular.manager.domain.repository.ProfileRepository

class DeleteProfileUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(profile: Profile) {
        repository.deleteProfile(profile)
    }
}
