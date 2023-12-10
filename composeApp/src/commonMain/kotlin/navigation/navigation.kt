package navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import ui.screens.home.HomeScreenUi
import ui.screens.register.RegisterScreenUi

@Composable
fun Nav() {
    val navigator = rememberNavigator()

    NavHost(
        navigator = navigator,
        navTransition = NavTransition(),
        initialRoute = Screens.Register.route
    ) {
        scene(Screens.Home.route) {
            HomeScreenUi(modifier = Modifier.fillMaxSize())
        }

        scene(Screens.Login.route) {
        }

        scene(Screens.Register.route) {
            RegisterScreenUi(modifier = Modifier.fillMaxSize())
        }
    }
}