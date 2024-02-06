package navigation

import data.app.AppPreferences
import domain.AuthenticationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class SharedViewModel(
    authenticationUseCase: AuthenticationUseCase,
    private val appPreferences: AppPreferences,
) : ViewModel() {
    private val _uiState: MutableStateFlow<SharedState> = MutableStateFlow(SharedState())
    val uiState: StateFlow<SharedState> = _uiState

    init {
        viewModelScope.launch {
            authenticationUseCase.getAuthState().collect { authState ->
                println("authState: $authState")
                _uiState.update { it.copy(authState = authState) }

                if (uiState.value.initializing) {
                    appPreferences.getIsOnboardingComplete().let { complete ->
                        _uiState.update { it.copy(setupComplete = complete, initializing = false) }
                    }
                }
            }
        }
    }

}
