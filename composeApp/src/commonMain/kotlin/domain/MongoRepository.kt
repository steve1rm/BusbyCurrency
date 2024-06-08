package domain

import domain.model.CurrencyModel
import kotlinx.coroutines.flow.Flow

interface MongoRepository {
    fun configureRealm()
    suspend fun insertCurrencyData(currencyModel: CurrencyModel)
    fun readCurrencyData(): Flow<RequestState<List<CurrencyModel>>>
    suspend fun cleanUp()
}