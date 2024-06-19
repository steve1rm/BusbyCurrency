package domain.model

sealed class CurrencyType(open val currencyCode: CurrencyCode) {
    data class Source(override val currencyCode: CurrencyCode) : CurrencyType(currencyCode)
    data class Target(override val currencyCode: CurrencyCode) : CurrencyType(currencyCode)
    data object None: CurrencyType(currencyCode = CurrencyCode.THB)
}