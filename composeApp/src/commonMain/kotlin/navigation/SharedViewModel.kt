package navigation

import domain.AuthenticationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class SharedViewModel(private val authenticationUseCase: AuthenticationUseCase) : ViewModel() {
    private val _uiState: MutableStateFlow<SharedState> = MutableStateFlow(SharedState(isUserLoggedIn = false))
    val uiState: StateFlow<SharedState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            checkLoginState()
        }
    }

    private suspend fun checkLoginState() {
        _uiState.update {
            it.copy(isUserLoggedIn = authenticationUseCase.getCurrentUser() != null)
        }
    }
}