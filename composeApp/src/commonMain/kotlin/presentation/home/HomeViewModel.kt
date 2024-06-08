package presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.CurrencyApiService
import domain.MongoRepository
import domain.PreferenceRepository
import domain.RequestState
import domain.model.CurrencyModel
import domain.model.RateStatus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class HomeViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val currencyApiService: CurrencyApiService,
    private val mongoRepository: MongoRepository
) : ScreenModel {

    var ratesStatus by mutableStateOf(RateStatus.Idle)
        private set

    var sourceCurrency = mutableStateOf<RequestState<CurrencyModel>>(RequestState.Idle)
        private set

    var targetCurrency: State<RequestState<CurrencyModel>> = mutableStateOf(RequestState.Idle)
        private set

    var allCurrencies = mutableStateListOf(CurrencyModel())
        private set

    init {
        preferenceRepository.currenyRateFlow.onEach { isFresh ->
            ratesStatus = if(isFresh) {
                println(RateStatus.Fresh.name)
                RateStatus.Fresh
            }
            else {
                println(RateStatus.Stale.name)
                RateStatus.Stale
            }
        }.launchIn(screenModelScope)

        fetchNewRates()
    }

    fun homeEvents(events: HomeEvents) {
        when(events) {
            HomeEvents.RefreshRates -> {
                fetchNewRates()
            }
        }
    }

    private fun fetchNewRates() {
        screenModelScope.launch {
            try {
                val localCache = mongoRepository.readCurrencyData().first()
                if(localCache.isSuccess()) {
                    if(localCache.getSuccessData().isNullOrEmpty()) {
                        println("HomeViewModel: DATABASE IS FULL")
                        localCache.getSuccessData()?.let { listOfCurrencies ->
                            allCurrencies.addAll(listOfCurrencies)
                        }

                        if(!preferenceRepository.isDataFresh(Clock.System.now().toEpochMilliseconds())) {
                            cacheCurrencyData()
                        }
                        else {
                            println("HomeViewModel: DATA IS FRESH")
                        }
                    }
                    else {
                        println("HomeViewModel: DATABASE NEEDS DATA")
                        cacheCurrencyData()
                    }
                }
                else if(localCache.isFailure()){
                    println("HomeViewModel: ERROR READING LOCAL DATABASE ${localCache.getFailureMessage()}")
                }
                getSaveRateStatus()
            }
            catch(exception: Exception) {
                println(exception.message)
            }
        }
    }

    private suspend fun cacheCurrencyData() {
        val fetchData = currencyApiService.getLatestExchangeRates()

        if (fetchData.isSuccess()) {
            mongoRepository.cleanUp()

            fetchData.getSuccessData()?.map { currencyModel ->
                println("HomeViewModel: ADDING ${currencyModel.code}")
                mongoRepository.insertCurrencyData(currencyModel)
            }
            println("HomeViewModel: UPDATING allCurrencies")
            fetchData.getSuccessData()?.let { listOfCurrencyModel ->
                allCurrencies.addAll(listOfCurrencyModel)
            }
        }
        else if(fetchData.isFailure()) {
            println("HomeViewModel: FETCHING FAILED ${fetchData.getFailureMessage() ?: ""}")
        }
    }

    override fun onDispose() {
        super.onDispose()
        println("ON_DISPOSE for ${HomeViewModel::class.simpleName}")
    }

    private fun getSaveRateStatus() {
        screenModelScope.launch {
            val areRatesFresh = preferenceRepository.isDataFresh(
                currentTimeStamp = Clock.System.now().toEpochMilliseconds())

            ratesStatus = if(areRatesFresh) {
                RateStatus.Fresh
            }
            else {
                RateStatus.Stale
            }
        }
    }
}