package domain

import domain.model.CurrencyCode
import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    val currencyRateFlow: Flow<Boolean>

    suspend fun saveLastUpdated(lastUpdated: String)
    suspend fun isDataFresh(currentTimeStamp: Long): Boolean
    suspend fun saveSourceCurrencyCode(code: String)
    suspend fun saveTargetCurrencyCode(code: String)
    fun readSourceCurrencyCode(): Flow<CurrencyCode>
    fun readTargetCurrencyCode(): Flow<CurrencyCode>
}