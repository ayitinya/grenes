package ui.screens.user

import me.ayitinya.grenes.data.users.User


sealed class ProfileScreenEvent {
    object OnFollowClick : ProfileScreenEvent()
    object OnUnFollowClick : ProfileScreenEvent()
    object OnEditProfileClick : ProfileScreenEvent()
    object OnBackClick : ProfileScreenEvent()
    object OnSettingsClick : ProfileScreenEvent()
    object OnLogoutClick : ProfileScreenEvent()
    object OnFollowersClick : ProfileScreenEvent()
    object OnFollowingClick : ProfileScreenEvent()
    object OnPostsClick : ProfileScreenEvent()
    object OnPostClick : ProfileScreenEvent()
    object OnRetryClick : ProfileScreenEvent()
}

sealed class LoadingState {
    data object Loading : LoadingState()
    data object Success : LoadingState()
    data class Error(val message: String? = null) : LoadingState()
}

data class ProfileScreenState(
    val isOwnUser: Boolean = false,
    val loadingState: LoadingState = LoadingState.Loading,
    val isFollowing: Boolean = false,
    val user: User? = null,
)