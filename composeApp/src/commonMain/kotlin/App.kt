import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import di.commonModules
import di.platformModules
import moe.tlaster.precompose.PreComposeApp
import navigation.Nav
import org.koin.compose.KoinApplication
import ui.theme.AppTheme

@Composable
fun App(useDarkTheme: Boolean = true, dynamicColor: Boolean = false) {
    KoinApplication(application = {
        platformModules.forEach { modules(it) }
        commonModules(isProduction = true).forEach { modules(it) }
    }) {
        PreComposeApp {
            AppTheme(dynamicColor = dynamicColor, useDarkTheme = useDarkTheme) {
                Nav(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
