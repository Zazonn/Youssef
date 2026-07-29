package com.singular.manager.di

import android.content.Context
import androidx.room.Room
import com.singular.manager.data.db.AppDatabase
import com.singular.manager.data.repository.GameRepositoryImpl
import com.singular.manager.data.repository.ProfileRepositoryImpl
import com.singular.manager.data.repository.ProxyRepositoryImpl
import com.singular.manager.domain.repository.GameRepository
import com.singular.manager.domain.repository.ProfileRepository
import com.singular.manager.domain.repository.ProxyRepository
import com.singular.manager.domain.usecase.*
import com.singular.manager.presentation.dashboard.DashboardViewModel
import com.singular.manager.presentation.logger.LoggerViewModel
import com.singular.manager.presentation.profile_manager.ProfileManagerViewModel
import com.singular.manager.root.RootDataManager

object AppModule {
    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context.applicationContext
    }

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "singular_manager_db").build()
    }

    private val profileRepository: ProfileRepository by lazy { ProfileRepositoryImpl(database.profileDao()) }
    private val gameRepository: GameRepository by lazy { GameRepositoryImpl(database.gameDao()) }
    private val proxyRepository: ProxyRepository by lazy { ProxyRepositoryImpl(database.proxyDao()) }
    private val rootDataManager: RootDataManager by lazy { RootDataManager() }

    fun provideDashboardViewModel() = DashboardViewModel(profileRepository, gameRepository)
    
    fun provideProfileManagerViewModel(): ProfileManagerViewModel {
        val useCase = BuildProfileFromDeviceUseCase(rootDataManager)
        return ProfileManagerViewModel(profileRepository, useCase)
    }
    
    fun provideLoggerViewModel() = LoggerViewModel()
}
