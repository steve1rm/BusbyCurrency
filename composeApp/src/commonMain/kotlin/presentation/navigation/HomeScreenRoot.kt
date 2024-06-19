package presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import presentation.home.HomeScreen
import presentation.home.HomeViewModel

object HomeScreenRoot : Screen {

    @Composable
    override fun Content() {
        val homeViewModel = getScreenModel<HomeViewModel>()

        val ratesState = homeViewModel.ratesStatus
        val source = homeViewModel.sourceCurrency
        val target = homeViewModel.targetCurrency
        val allCurrencies = homeViewModel.allCurrencies

        HomeScreen(
            onHomeEvents = { event ->
                homeViewModel.homeEvents(event)
            },
            rateStatus = ratesState,
            source = source,
            target = target,
            allCurrencies = allCurrencies
        )
    }
}