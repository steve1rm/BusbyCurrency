package domain

import domain.model.CurrencyModel

interface CurrencyApiService {
    suspend fun getLatestExchangeRates(): RequestState<List<CurrencyModel>>
}