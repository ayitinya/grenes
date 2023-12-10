package ui.screens.onboarding.authentication

import domain.AuthenticationUseCase
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope


class AuthScreenViewModel(private val authenticationUseCase: AuthenticationUseCase) : ViewModel() {
    private val _uiState = MutableUiState()
    val uiState: UiState = _uiState

    fun onEmailChange(email: String) {
        _uiState.email = email
    }

    fun onPasswordChange(password: String) {
        _uiState.password = password
    }


    suspend fun sendSignInLinkToEmail(email: String) {
        _uiState.authFlowState = AuthFlowState.Loading

        authenticationUseCase.sendSignInLinkToEmail(email = email, onEmailSent = {
            _uiState.authFlowState = AuthFlowState.EmailSent
        }, onError = { error ->
            _uiState.authFlowState = AuthFlowState.Error(error)
        })
    }

    fun fetchSignInMethodsForEmail() {
        _uiState.authFlowState = AuthFlowState.Loading

        viewModelScope.launch {
            val signInMethods = authenticationUseCase.fetchSignInMethodsForEmail(uiState.email)
            println("signInMethods: $signInMethods")
            if (signInMethods.isEmpty()) {
                _uiState.authFlowState = AuthFlowState.UserDoesNotExist
            } else {
                _uiState.authFlowState = AuthFlowState.UserExist(signInMethods)
            }
        }
    }

    fun signInWithEmailAndPassword() {
        viewModelScope.launch {
            try {
                authenticationUseCase.signInWithEmailAndPassword(email = uiState.email, password = uiState.password)
                _uiState.authFlowState = AuthFlowState.UserLoggedIn
            } catch (exception: Exception) {
                _uiState.authFlowState = AuthFlowState.Error(exception.message ?: "Unknown Error")
            }
        }
    }

    fun createUserWithEmailAndPassword() {
        viewModelScope.launch {
            try {
                authenticationUseCase.createUserWithEmailAndPassword(email = uiState.email, password = uiState.password)
                _uiState.authFlowState = AuthFlowState.UserCreated
            } catch (exception: Exception) {
                _uiState.authFlowState = AuthFlowState.Error(exception.message ?: "Unknown Error")
            }
        }
    }

    fun retry() {
        _uiState.authFlowState = AuthFlowState.Idle
        _uiState.email = ""
        _uiState.password = ""
    }
}