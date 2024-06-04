package presentation.home

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import data.remote.api.CurrencyApiServiceImp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen() {
    LaunchedEffect(key1 = true) {
        CurrencyApiServiceImp().getLatestExchangeRates()
    }
}

@Composable
@Preview()
fun PreviewHomeScreen() {
    MaterialTheme {
        HomeScreen()
    }
}