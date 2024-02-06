package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val path: String) {
    sealed class MainNavigation(subPath: String, val icon: ImageVector, val label: String) : Screens(subPath) {
        data object Challenges : MainNavigation("challenges", Icons.Default.EventNote, "Challenges")

        data object Feed : MainNavigation("home", Icons.Default.Home, "Feed")

        data object Leaderboard : MainNavigation("leaderboard", Icons.Default.Leaderboard, "Leaderboard")

        data object Profile : MainNavigation("profile/{uid}?", Icons.Default.Person, "Profile")

        companion object {
            fun profileRoute(uid: String? = null): String =
                Profile.path.replace("/{uid}", if (uid != null) "/$uid" else "")
        }

    }

    sealed class Onboarding(subRoute: String) : Screens("onboarding/$subRoute") {
        data object Auth : Onboarding("auth")
        data object Profile : Onboarding("profile")
        data object Landing : Onboarding("landing")
    }


    /*
    * This is a workaround because static properties are not initialized
    * and would cause a NullPointerException
    * See: https://youtrack.jetbrains.com/issue/KT-8970/Object-is-uninitialized-null-when-accessed-from-static-context-ex.-companion-object-with-initialization-loop
    * */
    private object Initializer {
        val navScreens: List<MainNavigation> = listOf(
            MainNavigation.Feed,
            MainNavigation.Challenges,
            MainNavigation.Leaderboard,
            MainNavigation.Profile,
        )
    }

    companion object {
        val navScreens: List<MainNavigation>
            get() = Initializer.navScreens
    }
}