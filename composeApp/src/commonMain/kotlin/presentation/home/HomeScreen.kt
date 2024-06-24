package presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import domain.RequestState
import domain.model.CurrencyModel
import domain.model.CurrencyType
import domain.model.RateStatus
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.components.CurrencyPickerDialog
import presentation.components.HomeBody
import presentation.components.HomeHeader
import ui.theme.surfaceColor

@Composable
fun HomeScreen(
    rateStatus: RateStatus,
    sourceCurrency: RequestState<CurrencyModel>,
    targetCurrency: RequestState<CurrencyModel>,
    allCurrencies: SnapshotStateList<CurrencyModel>,
    onHomeEvents: (HomeEvents) -> Unit,
) {
    var amount by rememberSaveable {
        mutableStateOf(0.0)
    }

    var selectedCurrencyType: CurrencyType by remember {
        mutableStateOf(CurrencyType.None)
    }

    var isDialogOpened by remember {
        mutableStateOf(false)
    }

    if(isDialogOpened&& selectedCurrencyType != CurrencyType.None) {
        CurrencyPickerDialog(
            listOfCurrency = allCurrencies,
            currencyType = selectedCurrencyType,
            onConfirmedClicked = { currencyCode ->
                if(selectedCurrencyType is CurrencyType.Source) {
                    onHomeEvents(HomeEvents.saveSourceCurrencyCode(currencyCode))
                }
                else if(selectedCurrencyType is CurrencyType.Target) {
                    onHomeEvents(HomeEvents.saveTargetCurrencyCode(currencyCode))
                }

                selectedCurrencyType = CurrencyType.None
                isDialogOpened = false

            },
            onDismiss = {
                selectedCurrencyType = CurrencyType.None
                isDialogOpened = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = surfaceColor)
    ) {
        HomeHeader(
            ratesStatus = rateStatus,
            source = sourceCurrency,
            target = targetCurrency,
            onRateRefreshClicked = {
                onHomeEvents(HomeEvents.RefreshRates)
            },
            onSwitchClicked = {
                onHomeEvents(HomeEvents.SwitchCurrency)
            },
            amount = amount,
            onAmountChange = { newValue ->
                amount = newValue
            },
            onCurrencyTypeClicked = { currencyType ->
                isDialogOpened = true
                selectedCurrencyType = currencyType
            }
        )

        HomeBody(
            source = sourceCurrency,
            target = targetCurrency,
            amount = amount
        )
    }
}

@Composable
@Preview
fun PreviewHomeScreen() {
    MaterialTheme {
        HomeScreen(
            rateStatus = RateStatus.Stale,
            onHomeEvents = {},
            sourceCurrency = RequestState.Success(CurrencyModel()),
            allCurrencies = SnapshotStateList(),
            targetCurrency = RequestState.Failure("Failed")
        )
    }
}