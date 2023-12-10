package navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import ui.home.HomeScreenUi
import ui.home.HomeViewModel

@Composable
fun Nav() {
    val navigator = rememberNavigator()

    NavHost(
        navigator = navigator,
        navTransition = NavTransition(),
        initialRoute = "/home"
    ) {
        scene("/home") {
            HomeScreenUi(modifier = Modifier.fillMaxSize(),  koinViewModel(HomeViewModel::class))
        }
    }
}