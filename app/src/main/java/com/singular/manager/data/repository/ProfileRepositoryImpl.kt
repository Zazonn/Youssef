package com.singular.manager.data.repository

import com.singular.manager.data.db.dao.ProfileDao
import com.singular.manager.data.db.entity.ProfileEntity
import com.singular.manager.domain.model.Profile
import com.singular.manager.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileRepositoryImpl(private val profileDao: ProfileDao) : ProfileRepository {

    override fun getAllProfiles(): Flow<List<Profile>> {
        return profileDao.getAllProfiles().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getProfileById(profileId: Int): Profile? {
        return profileDao.getProfileById(profileId)?.toDomain()
    }

    override suspend fun insertProfile(profile: Profile) {
        profileDao.insertProfile(profile.toEntity())
    }

    override suspend fun updateProfile(profile: Profile) {
        profileDao.updateProfile(profile.toEntity())
    }

    override suspend fun deleteProfile(profile: Profile) {
        profileDao.deleteProfile(profile.toEntity())
    }

    override fun countProfilesMissingGaid(): Flow<Int> {
        return profileDao.countProfilesMissingGaid()
    }

    override fun getDuplicateEmails(): Flow<List<String>> {
        return profileDao.getDuplicateEmails()
    }

    override fun getDuplicateUids(): Flow<List<String>> {
        return profileDao.getDuplicateUids()
    }

    private fun ProfileEntity.toDomain(): Profile {
        return Profile(id, name, gaid, email, uid, createdAt)
    }

    private fun Profile.toEntity(): ProfileEntity {
        return ProfileEntity(id, name, gaid, email, uid, createdAt)
    }
}
