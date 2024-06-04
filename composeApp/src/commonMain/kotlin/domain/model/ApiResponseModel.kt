package domain.model


import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseModel(
    val meta: MetaModel,
    val data: Map<String, CurrencyModel>,
)