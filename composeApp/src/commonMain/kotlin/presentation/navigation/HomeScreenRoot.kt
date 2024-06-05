package presentation.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import presentation.home.HomeScreen

object HomeScreenRoot : Screen {


    @Composable
    override fun Content() {
        HomeScreen()
    }
}