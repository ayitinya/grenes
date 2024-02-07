package navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import data.auth.AuthState
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import ui.screens.challengedetails.ChallengeDetailScreen
import ui.screens.challenges.ChallengesScreenUi
import ui.screens.home.HomeScreenUi
import ui.screens.onboarding.LandingScreenUi
import ui.screens.onboarding.authentication.AuthScreen
import ui.screens.onboarding.profile.ProfileScreen
import ui.screens.user.UserScreen

@Composable
fun Nav(
    sharedViewModel: SharedViewModel = koinViewModel(SharedViewModel::class),
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val navigator = rememberNavigator()

    val state = sharedViewModel.uiState.collectAsState()

    if (state.value.initializing) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    } else {
        val initialRoute: String = when (state.value.authState) {
            AuthState.Anonymous -> Screens.MainNavigation.Feed.path

            is AuthState.Authenticated -> {
                if (state.value.setupComplete) {
                    Screens.MainNavigation.Feed.path
                } else Screens.Onboarding.Profile.path
            }

            AuthState.NotAuthenticated -> Screens.Onboarding.Landing.path

            else -> ""
        }

        Scaffold(bottomBar = {
            val navBackStackEntry = navigator.currentEntry.collectAsState(initial = null)

            val currentDestination = navBackStackEntry.value?.route?.route

            if ((state.value.authState is AuthState.Authenticated || state.value.authState is AuthState.Anonymous) && currentDestination?.contains(
                    "onboarding"
                ) == false
            ) {

                val isMainNavigationRoute = Screens.navScreens.any { it.path == currentDestination }



                if (isMainNavigationRoute) {
                    NavigationBar {
                        Screens.navScreens.forEach { screen ->
                            NavigationBarItem(icon = {
                                Icon(
                                    imageVector = screen.icon, contentDescription = screen.label
                                )
                            }, label = { Text(screen.label) }, selected = currentDestination == screen.path, onClick = {
                                if (currentDestination != screen.path) {
                                    navigator.navigate(
                                        route = if (screen is Screens.MainNavigation.Profile) {
                                            Screens.MainNavigation.profileRoute()
                                        } else
                                            screen.path
                                    )
                                }
                            })
                        }
                    }
                }
            }
        }, modifier = modifier) { paddingValues ->
            NavHost(
                modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
                navigator = navigator,
                navTransition = NavTransition(),
                initialRoute = initialRoute
            ) {
                scene(Screens.MainNavigation.Feed.path) {
                    HomeScreenUi(modifier = Modifier.fillMaxSize())
                }

                scene(Screens.MainNavigation.Profile.path) { backStackEntry ->
                    val uid: String? = backStackEntry.path<String>("uid")

                    UserScreen(uid = uid ?: "", modifier = Modifier.fillMaxSize())
                }

                scene(Screens.Onboarding.Profile.path) {
                    ProfileScreen(
                        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing),
                    ) {
                        navigator.navigate(Screens.MainNavigation.Feed.path)
                    }
                }

                scene(Screens.Onboarding.Landing.path) {
                    LandingScreenUi(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.navigationBars),
                        onGoogleButtonClick = { },
                        onEmailButtonClick = { navigator.navigate(Screens.Onboarding.Auth.path) })
                }

                scene(Screens.Onboarding.Auth.path) {
                    AuthScreen(
                        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.navigationBars),
                        navigateToOnboarding = {
                            navigator.navigate(Screens.Onboarding.Profile.path)
                        },
                        navigateToFeed = {
                            navigator.navigate(Screens.MainNavigation.Feed.path)
                        },
                        onBackButtonClick = navigator::goBack
                    )
                }

                scene(Screens.MainNavigation.Challenges.path) {
                    ChallengesScreenUi(modifier = Modifier.fillMaxSize(), navigateToChallengeDetails = {
                        navigator.navigate(Screens.ChallengeDetail.path.replace("{uid}", it))
                    })
                }

                scene(Screens.ChallengeDetail.path) { backStackEntry ->
                    val uid: String? = backStackEntry.path<String>("uid")

                    uid?.let {
                        ChallengeDetailScreen(
                            uid = uid,
                            modifier = Modifier.fillMaxSize(),
                            navigateBack = navigator::goBack
                        )
                    }
                }
            }
        }
    }
}