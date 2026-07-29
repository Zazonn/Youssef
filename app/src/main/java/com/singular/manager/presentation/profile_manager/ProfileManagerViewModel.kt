package com.singular.manager.presentation.profile_manager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singular.manager.domain.model.Profile
import com.singular.manager.domain.repository.ProfileRepository
import com.singular.manager.domain.usecase.BuildProfileFromDeviceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileManagerUiState(
    val profiles: List<Profile> = emptyList(),
    val selectedProfile: Profile? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val showProfileDialog: Boolean = false,
    val dialogProfile: Profile? = null // Profile being edited or created
)

class ProfileManagerViewModel(
    private val profileRepository: ProfileRepository,
    private val buildProfileFromDeviceUseCase: BuildProfileFromDeviceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileManagerUiState())
    val uiState: StateFlow<ProfileManagerUiState> = _uiState.asStateFlow()

    init {
        loadProfiles()
    }

    private fun loadProfiles() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            profileRepository.getAllProfiles().collect {
                _uiState.update { currentState ->
                    currentState.copy(profiles = it, isLoading = false)
                }
            }
        }
    }

    fun selectProfile(profile: Profile?) {
        _uiState.update { it.copy(selectedProfile = profile) }
    }

    fun showAddEditProfileDialog(profile: Profile? = null) {
        _uiState.update { it.copy(showProfileDialog = true, dialogProfile = profile) }
    }

    fun dismissProfileDialog() {
        _uiState.update { it.copy(showProfileDialog = false, dialogProfile = null) }
    }

    fun saveProfile(profile: Profile) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                if (profile.id == 0) {
                    profileRepository.insertProfile(profile)
                } else {
                    profileRepository.updateProfile(profile)
                }
                dismissProfileDialog()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.localizedMessage, isLoading = false) }
            }
        }
    }

    fun deleteProfile(profile: Profile) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                profileRepository.deleteProfile(profile)
                _uiState.update { it.copy(selectedProfile = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.localizedMessage, isLoading = false) }
            }
        }
    }

    fun buildProfileFromDevice() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val newProfile = buildProfileFromDeviceUseCase()
                if (newProfile != null) {
                    profileRepository.insertProfile(newProfile)
                    _uiState.update { it.copy(isLoading = false) }
                } else {
                    _uiState.update { it.copy(error = "Failed to build profile from device. Is the device rooted?", isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.localizedMessage, isLoading = false) }
            }
        }
    }
}
