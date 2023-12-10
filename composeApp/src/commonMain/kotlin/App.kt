import androidx.compose.runtime.Composable
import di.commonModules
import moe.tlaster.precompose.PreComposeApp
import navigation.Nav
import org.koin.compose.KoinApplication
import ui.theme.AppTheme

@Composable
fun App(useDarkTheme: Boolean = true, dynamicColor: Boolean = false) {
    KoinApplication(application = {
        commonModules.forEach { modules(it) }
    }) {
        PreComposeApp {
            AppTheme(dynamicColor = dynamicColor, useDarkTheme = useDarkTheme) {
                Nav()
            }
        }
    }
}