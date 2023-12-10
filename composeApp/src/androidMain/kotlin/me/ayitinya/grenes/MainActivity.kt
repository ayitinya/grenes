package me.ayitinya.grenes

import MainView
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()

            DisposableEffect(systemUiController, useDarkIcons) {
                systemUiController.setStatusBarColor(
                    color = Color.Transparent, darkIcons = useDarkIcons
                )

                onDispose { }
            }

            MainView(
                useDarkTheme = isSystemInDarkTheme(),
                dynamicColor = true,
            )
        }
    }
}

//@Preview
//@Composable
//fun AppAndroidPreview() {
////    App()
//}