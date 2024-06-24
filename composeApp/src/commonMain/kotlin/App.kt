import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import di.initializeKoin
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.navigation.HomeScreenRoot
import ui.theme.DarkColors
import ui.theme.LightColors

@Composable
@Preview
fun App() {
    val colors = if (!isSystemInDarkTheme()) LightColors else DarkColors

    MaterialTheme(colorScheme = colors) {
        Navigator(screen = HomeScreenRoot)
    }
}