package ui.screens.challenges

import data.challenges.ChallengesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.ayitinya.grenes.data.challenges.Challenge
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class ChallengesViewModel(private val challengesRepository: ChallengesRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<ChallengeUiState> = MutableStateFlow(ChallengeUiState())
    val uiState: StateFlow<ChallengeUiState> = _uiState

    init {
        viewModelScope.launch {
            val challenges = challengesRepository.getChallenges()

            _uiState.update { state ->
                state.copy(monthlyChallenges = challenges.filter { challenge ->
                    challenge.challengeTypes.any {
                        it.name.equals(
                            "monthly", ignoreCase = true
                        )
                    }
                }, dailyChallenges = challenges.filter { challenge ->
                    challenge.challengeTypes.any {
                        it.name.equals(
                            "daily", ignoreCase = true
                        )
                    }
                }, loading = false
                )
            }
        }
    }

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
    }

}

data class ChallengeUiState(
    val selectedTabIndex: Int = 0,
    val monthlyChallenges: List<Challenge> = emptyList(),
    val dailyChallenges: List<Challenge> = emptyList(),
    val loading: Boolean = true,
)