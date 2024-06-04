package domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MetaModel(
    @SerialName("last_updated_at")
    val lastUpdatedAt: String
)