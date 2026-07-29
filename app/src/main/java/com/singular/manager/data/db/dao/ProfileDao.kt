package com.singular.manager.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.singular.manager.data.db.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profiles ORDER BY createdAt DESC")
    fun getAllProfiles(): Flow<List<ProfileEntity>>

    @Query("SELECT * FROM profiles WHERE id = :profileId")
    suspend fun getProfileById(profileId: Int): ProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileEntity)

    @Update
    suspend fun updateProfile(profile: ProfileEntity)

    @Delete
    suspend fun deleteProfile(profile: ProfileEntity)

    @Query("SELECT COUNT(*) FROM profiles WHERE gaid IS NULL OR gaid = ''")
    fun countProfilesMissingGaid(): Flow<Int>

    @Query("SELECT email FROM profiles GROUP BY email HAVING COUNT(email) > 1")
    fun getDuplicateEmails(): Flow<List<String>>

    @Query("SELECT uid FROM profiles GROUP BY uid HAVING COUNT(uid) > 1")
    fun getDuplicateUids(): Flow<List<String>>
}
