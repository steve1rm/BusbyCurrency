package presentation.home

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import domain.model.RateStatus
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.components.HomeHeader

@Composable
fun HomeScreen(
    rateStatus: RateStatus,
    onHomeEvents: (HomeEvents) -> Unit,
) {
    HomeHeader(
        ratesStatus = rateStatus,
        onRateRefreshClicked = {
            onHomeEvents(HomeEvents.RefreshRates)
        }
    )
}

@Composable
@Preview()
fun PreviewHomeScreen() {
    MaterialTheme {
        HomeScreen(
            rateStatus = RateStatus.Stale,
            onHomeEvents = {}
        )
    }
}