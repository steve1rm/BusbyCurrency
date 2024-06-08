package presentation.home

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
    var amount by rememberSaveable {
        mutableStateOf(0.0)
    }

    HomeHeader(
        ratesStatus = rateStatus,
        source = source,
        target = target,
        onRateRefreshClicked = {
            onHomeEvents(HomeEvents.RefreshRates)
        },
        onSwitchClicked = {},
        amount = 0.0,
        onAmountChange = { newValue ->
            amount = newValue
        }
    )
}

@Composable
@Preview()
fun PreviewHomeScreen() {
    MaterialTheme {
        HomeScreen(
            rateStatus = RateStatus.Stale,
            onHomeEvents = {},
            source = RequestState.Success(CurrencyModel()),
            target = RequestState.Failure("Failed")
        )
    }
}