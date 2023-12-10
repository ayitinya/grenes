package navigation

sealed class Screens(val route: String) {
    data object Home : Screens("home")

    sealed class Onboarding(private val subRoute: String) : Screens("onboarding/$subRoute") {
        data object Auth : Onboarding("auth")
        data object Profile : Onboarding("profile")
        data object Landing : Onboarding("landing")
    }

    companion object {
        val allScreens: List<Screens> = listOf(
            Home,
        )
    }
}