package presentation.home

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import domain.RequestState
import domain.model.CurrencyModel
import domain.model.RateStatus
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.components.HomeHeader

@Composable
fun HomeScreen(
    rateStatus: RateStatus,
    source: RequestState<CurrencyModel>,
    target: RequestState<CurrencyModel>,
    onHomeEvents: (HomeEvents) -> Unit,
) {
    HomeHeader(
        ratesStatus = rateStatus,
        source = source,
        target = target,
        onRateRefreshClicked = {
            onHomeEvents(HomeEvents.RefreshRates)
        },
        onSwitchClicked = {}
    )
}

@Composable
@Preview()
fun PreviewHomeScreen() {
    MaterialTheme {
        HomeScreen(
            rateStatus = RateStatus.Stale,
            onHomeEvents = {},
            source = RequestState.Success(CurrencyModel("", 0.0)),
            target = RequestState.Failure("Failed")
        )
    }
}