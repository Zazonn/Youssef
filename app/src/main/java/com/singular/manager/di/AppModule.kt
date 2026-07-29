package com.singular.manager.di

import android.content.Context
import androidx.room.Room
import com.singular.manager.data.db.AppDatabase
import com.singular.manager.data.db.dao.GameDao
import com.singular.manager.data.db.dao.ProfileDao
import com.singular.manager.data.db.dao.ProxyDao
import com.singular.manager.data.remote.SingularApiService
import com.singular.manager.data.repository.GameRepositoryImpl
import com.singular.manager.data.repository.ProfileRepositoryImpl
import com.singular.manager.data.repository.ProxyRepositoryImpl
import com.singular.manager.domain.repository.GameRepository
import com.singular.manager.domain.repository.ProfileRepository
import com.singular.manager.domain.repository.ProxyRepository
import com.singular.manager.domain.usecase.BuildProfileFromDeviceUseCase
import com.singular.manager.domain.usecase.DeleteGameUseCase
import com.singular.manager.domain.usecase.DeleteProfileUseCase
import com.singular.manager.domain.usecase.GetGameByIdUseCase
import com.singular.manager.domain.usecase.GetGamesUseCase
import com.singular.manager.domain.usecase.GetProfileByIdUseCase
import com.singular.manager.domain.usecase.GetProfilesUseCase
import com.singular.manager.domain.usecase.GetProxiesUseCase
import com.singular.manager.domain.usecase.GetProxyByIdUseCase
import com.singular.manager.domain.usecase.InsertGameUseCase
import com.singular.manager.domain.usecase.InsertProfileUseCase
import com.singular.manager.domain.usecase.InsertProxyUseCase
import com.singular.manager.domain.usecase.UpdateGameUseCase
import com.singular.manager.domain.usecase.UpdateProfileUseCase
import com.singular.manager.domain.usecase.UpdateProxyUseCase
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
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "singular_manager_db"
        ).build()
    }

    // DAOs
    private val profileDao: ProfileDao by lazy { database.profileDao() }
    private val gameDao: GameDao by lazy { database.gameDao() }
    private val proxyDao: ProxyDao by lazy { database.proxyDao() }

    // Repositories
    private val profileRepository: ProfileRepository by lazy { ProfileRepositoryImpl(profileDao) }
    private val gameRepository: GameRepository by lazy { GameRepositoryImpl(gameDao) }
    private val proxyRepository: ProxyRepository by lazy { ProxyRepositoryImpl(proxyDao) }

    // Root Data Manager
    private val rootDataManager: RootDataManager by lazy { RootDataManager() }

    // Use Cases
    val getProfilesUseCase: GetProfilesUseCase by lazy { GetProfilesUseCase(profileRepository) }
    val getProfileByIdUseCase: GetProfileByIdUseCase by lazy { GetProfileByIdUseCase(profileRepository) }
    val insertProfileUseCase: InsertProfileUseCase by lazy { InsertProfileUseCase(profileRepository) }
    val updateProfileUseCase: UpdateProfileUseCase by lazy { UpdateProfileUseCase(profileRepository) }
    val deleteProfileUseCase: DeleteProfileUseCase by lazy { DeleteProfileUseCase(profileRepository) }
    val buildProfileFromDeviceUseCase: BuildProfileFromDeviceUseCase by lazy { BuildProfileFromDeviceUseCase(rootDataManager) }

    val getGamesUseCase: GetGamesUseCase by lazy { GetGamesUseCase(gameRepository) }
    val getGameByIdUseCase: GetGameByIdUseCase by lazy { GetGameByIdUseCase(gameRepository) }
    val insertGameUseCase: InsertGameUseCase by lazy { InsertGameUseCase(gameRepository) }
    val updateGameUseCase: UpdateGameUseCase by lazy { UpdateGameUseCase(gameRepository) }
    val deleteGameUseCase: DeleteGameUseCase by lazy { DeleteGameUseCase(gameRepository) }

    val getProxiesUseCase: GetProxiesUseCase by lazy { GetProxiesUseCase(proxyRepository) }
    val getProxyByIdUseCase: GetProxyByIdUseCase by lazy { GetProxyByIdUseCase(proxyRepository) }
    val insertProxyUseCase: InsertProxyUseCase by lazy { InsertProxyUseCase(proxyRepository) }
    val updateProxyUseCase: UpdateProxyUseCase by lazy { UpdateProxyUseCase(proxyRepository) }
    val deleteProxyUseCase: DeleteProxyUseCase by lazy { DeleteProxyUseCase(proxyRepository) }

    // Network
    private val okHttpClient by lazy { NetworkModule.provideOkHttpClient() }
    private val retrofit by lazy { NetworkModule.provideRetrofit(okHttpClient) }
    val singularApiService: SingularApiService by lazy { NetworkModule.provideSingularApiService(retrofit) }

    // ViewModels (using a factory pattern for now, or direct instantiation if no dependencies)
    fun provideDashboardViewModel(): DashboardViewModel {
        return DashboardViewModel(profileRepository, gameRepository)
    }

    fun provideProfileManagerViewModel(): ProfileManagerViewModel {
        return ProfileManagerViewModel(profileRepository, buildProfileFromDeviceUseCase)
    }

    fun provideLoggerViewModel(): LoggerViewModel {
        return LoggerViewModel()
    }
}
