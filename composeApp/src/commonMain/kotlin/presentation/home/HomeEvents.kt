package presentation.home

import domain.model.CurrencyCode

interface HomeEvents {
    data object RefreshRates: HomeEvents
    data object SwitchCurrency: HomeEvents
    data class saveSourceCurrencyCode(val currencyCode: CurrencyCode) : HomeEvents
    data class saveTargetCurrencyCode(val currencyCode: CurrencyCode) : HomeEvents
}
