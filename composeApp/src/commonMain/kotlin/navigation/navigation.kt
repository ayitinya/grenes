package navigation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import ui.screens.home.HomeScreenUi
import ui.screens.onboarding.authentication.AuthScreen
import ui.screens.onboarding.LandingScreenUi
import ui.screens.onboarding.profile.ProfileScreen

@Composable
fun Nav() {
    val navigator = rememberNavigator()

    NavHost(
        navigator = navigator,
        navTransition = NavTransition(),
        initialRoute = Screens.Onboarding.Landing.route
    ) {
        scene(Screens.Home.route) {
            HomeScreenUi(modifier = Modifier.fillMaxSize())
        }

        scene(Screens.Onboarding.Profile.route) {
            ProfileScreen(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing))
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
                signUp = {
                    navigator.navigate(Screens.Onboarding.Profile.route)
                },
                login = {
                    navigator.navigate(Screens.Home.route)
                },
                onBackButtonClick = navigator::goBack
            )
        }
    }
}