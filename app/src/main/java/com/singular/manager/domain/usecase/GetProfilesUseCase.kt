package com.singular.manager.domain.usecase

import com.singular.manager.domain.model.Profile
import com.singular.manager.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetProfilesUseCase(private val repository: ProfileRepository) {
    operator fun invoke(): Flow<List<Profile>> {
        return repository.getAllProfiles()
    }
}
