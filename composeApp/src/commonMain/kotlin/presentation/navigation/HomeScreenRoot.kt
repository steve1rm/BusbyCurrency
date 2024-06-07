package presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import presentation.home.HomeScreen
import presentation.home.HomeViewModel

object HomeScreenRoot : Screen {

    @Composable
    override fun Content() {
        val homeViewModel = getScreenModel<HomeViewModel>()
        val ratesState = homeViewModel.ratesStatus
        val source by homeViewModel.sourceCurrency
        val target by homeViewModel.targetCurrency

        HomeScreen(
            onHomeEvents = { event ->
                homeViewModel.homeEvents(event)
            },
            rateStatus = ratesState,
            source = source,
            target = target
        )
    }
}