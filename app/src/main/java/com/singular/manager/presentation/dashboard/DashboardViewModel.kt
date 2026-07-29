package com.singular.manager.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singular.manager.domain.model.Game
import com.singular.manager.domain.repository.GameRepository
import com.singular.manager.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class DashboardUiState(
    val profilesMissingGaid: Int = 0,
    val duplicateEmails: List<String> = emptyList(),
    val duplicateUids: List<String> = emptyList(),
    val gamesWithoutEventTokens: List<Game> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class DashboardViewModel(
    private val profileRepository: ProfileRepository,
    private val gameRepository: GameRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            combine(
                profileRepository.countProfilesMissingGaid(),
                profileRepository.getDuplicateEmails(),
                profileRepository.getDuplicateUids(),
                gameRepository.getGamesWithoutEventTokens()
            ) { missingGaid, duplicateEmails, duplicateUids, gamesWithoutTokens ->
                _uiState.value.copy(
                    profilesMissingGaid = missingGaid,
                    duplicateEmails = duplicateEmails,
                    duplicateUids = duplicateUids,
                    gamesWithoutEventTokens = gamesWithoutTokens,
                    isLoading = false
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }
}
