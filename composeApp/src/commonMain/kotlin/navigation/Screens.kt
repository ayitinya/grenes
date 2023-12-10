package navigation

sealed class Screens(val route: String) {
    data object Home : Screens("home")
    data object Login : Screens("login")
    data object Register : Screens("register")
    data object Landing : Screens("landing")

    companion object {
        val allScreens: List<Screens> = listOf(
            Home, Login, Register, Landing
        )
    }
}