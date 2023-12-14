package navigation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import ui.screens.home.HomeScreenUi
import ui.screens.onboarding.authentication.AuthScreen
import ui.screens.onboarding.LandingScreenUi
import ui.screens.onboarding.profile.ProfileScreen

@Composable
fun Nav(sharedViewModel: SharedViewModel = koinViewModel(SharedViewModel::class)) {
    val navigator = rememberNavigator()

    val state = sharedViewModel.uiState.collectAsState()

    NavHost(
        navigator = navigator,
        navTransition = NavTransition(),
        initialRoute = if (state.value.isUserLoggedIn) Screens.Home.route else Screens.Onboarding.Landing.route
    ) {
        scene(Screens.Home.route) {
            HomeScreenUi(modifier = Modifier.fillMaxSize())
        }

        scene(Screens.Onboarding.Profile.route) { backStackEntry ->
            val email: String? = backStackEntry.path<String>("email")

            ProfileScreen(
                modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing),
                email = email,
                onSave = {
                    navigator.navigate(Screens.Home.route)
                })
        }

        scene(Screens.Onboarding.Landing.route) {
            LandingScreenUi(
                modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.navigationBars),
                onGoogleButtonClick = { },
                onEmailButtonClick = { navigator.navigate(Screens.Onboarding.Auth.route) })
        }

        scene(Screens.Onboarding.Auth.route) {
            AuthScreen(
                modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.navigationBars),
                signUp = { email ->
                    navigator.navigate("onboarding/profile/$email")
                },
                login = {
                    navigator.navigate(Screens.Home.route)
                },
                onBackButtonClick = navigator::goBack
            )
        }
    }
}