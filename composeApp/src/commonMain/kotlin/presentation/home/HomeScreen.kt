package presentation.home

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.components.HomeHeader

@Composable
fun HomeScreen() {
    HomeHeader()
}

@Composable
@Preview()
fun PreviewHomeScreen() {
    MaterialTheme {
        HomeScreen()
    }
}