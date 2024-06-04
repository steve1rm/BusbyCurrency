package data.remote.api

import domain.CurrencyApiService
import domain.RequestState
import domain.model.ApiResponseModel
import domain.model.CurrencyModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.logging.KtorSimpleLogger
import io.ktor.util.logging.Logger
import kotlinx.serialization.json.Json

class CurrencyApiServiceImp : CurrencyApiService {

    companion object {
        const val ENDPOINT = "https://api.currencyapi.com/v3/latest"
        const val API_KEY = "cur_live_5TjtBbUjAMaDIJBtqdqqjxwQEyW8Eq4EQDB0uDO1"
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(Logging) {
            this.level = LogLevel.ALL
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }

        install(DefaultRequest) {
            headers {
                append("apikey", API_KEY)
            }
        }
    }

    override suspend fun getLatestExchangeRates(): RequestState<List<CurrencyModel>> {
        return try {
            val response = httpClient.get(ENDPOINT)

            if(response.status.value == 200) {
                val apiResponse = Json.decodeFromString<ApiResponseModel>(response.body())
                RequestState.Success(apiResponse.data.values.toList())
            }
            else {
                RequestState.Failure(message = "HTTP Error Code: ${response.status}")
            }
        } catch (e: Exception) {
            RequestState.Failure(message = e.message.toString())
        }
    }
}