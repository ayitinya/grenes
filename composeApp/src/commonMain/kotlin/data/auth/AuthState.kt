package data.auth

sealed class AuthState {
    data class Authenticated(val uid: String) : AuthState()
    data object NotAuthenticated : AuthState()
    data object Anonymous : AuthState()
}