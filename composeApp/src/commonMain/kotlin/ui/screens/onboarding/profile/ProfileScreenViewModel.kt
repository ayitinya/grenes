package ui.screens.onboarding.profile

import data.users.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class ProfileScreenViewModel(private val usersRepository: UsersRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<ProfileScreenState> =
        MutableStateFlow(ProfileScreenState(displayName = "", city = "", country = ""))
    val uiState: StateFlow<ProfileScreenState> = _uiState

    fun updateDisplayName(displayName: String) {
        _uiState.value = _uiState.value.copy(displayName = displayName)
    }

    fun updateCity(city: String) {
        _uiState.value = _uiState.value.copy(city = city)
    }

    fun updateCountry(country: String) {
        _uiState.value = _uiState.value.copy(country = country)
    }

    fun updateProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.updateProfile(
                _uiState.value.displayName,
                _uiState.value.city,
                _uiState.value.country
            )
        }
    }
}