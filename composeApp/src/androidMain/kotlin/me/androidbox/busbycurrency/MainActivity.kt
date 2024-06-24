package me.androidbox.busbycurrency

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import di.initializeKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeKoin()

        setContent {
            enableEdgeToEdge()
            App()

            val isDarkTheme = isSystemInDarkTheme()
            val view = LocalView.current

            if(!view.isInEditMode) {
                SideEffect {
                    val window = this.window
                    window.statusBarColor = Color.Transparent.toArgb()
                    window.navigationBarColor = Color.Transparent.toArgb()

                    val insetController = WindowCompat.getInsetsController(window, view)
                    insetController.isAppearanceLightStatusBars = false
                    insetController.isAppearanceLightNavigationBars = !isDarkTheme
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}