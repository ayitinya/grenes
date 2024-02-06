package ui.screens.user

import data.auth.AuthState
import data.users.UsersRepository
import domain.AuthenticationUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class ProfileScreenViewModel(
    private val uid: String,
    private val usersRepository: UsersRepository,
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<ProfileScreenState> = MutableStateFlow(
        ProfileScreenState()
    )

    val uiState: StateFlow<ProfileScreenState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            authenticationUseCase.getAuthState().take(1).collect { authState ->
                if (authState is AuthState.Authenticated) {
                    if (authState.uid == uid || uid.isEmpty()) {
                        _uiState.update {
                            it.copy(isOwnUser = true)
                        }
                    }

                    getUserInfo()

                }
            }
        }
    }

    private suspend fun getUserInfo() {
        if (uiState.value.isOwnUser) {
            try {
                usersRepository.getUser().let { user ->
                    if (user != null) {
                        _uiState.update {
                            it.copy(user = user, loadingState = LoadingState.Success)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update {
                    it.copy(loadingState = LoadingState.Error(e.message))
                }
            }
        } else {
            try {
                usersRepository.getUser(uid).let { user ->
                    if (user != null) {
                        _uiState.update {
                            it.copy(user = user, loadingState = LoadingState.Success)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update {
                    it.copy(loadingState = LoadingState.Error(e.message))
                }
            }

        }
    }
}