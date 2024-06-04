package domain.model


import kotlinx.serialization.Serializable

@Serializable
data class CurrencyModel(
    val code: String,
    val value: Double
)