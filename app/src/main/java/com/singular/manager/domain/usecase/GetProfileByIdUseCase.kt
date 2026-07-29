package com.singular.manager.domain.usecase

import com.singular.manager.domain.model.Profile
import com.singular.manager.domain.repository.ProfileRepository

class GetProfileByIdUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(profileId: Int): Profile? {
        return repository.getProfileById(profileId)
    }
}
