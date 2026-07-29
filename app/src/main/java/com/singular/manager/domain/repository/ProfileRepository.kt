package com.singular.manager.domain.repository

import com.singular.manager.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getAllProfiles(): Flow<List<Profile>>
    suspend fun getProfileById(profileId: Int): Profile?
    suspend fun insertProfile(profile: Profile)
    suspend fun updateProfile(profile: Profile)
    suspend fun deleteProfile(profile: Profile)
    fun countProfilesMissingGaid(): Flow<Int>
    fun getDuplicateEmails(): Flow<List<String>>
    fun getDuplicateUids(): Flow<List<String>>
}
