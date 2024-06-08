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
import kotlinx.coroutines.flow.single
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
        preferenceRepository.currencyRateFlow.onEach { isFresh ->
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

    private fun readSourceCurrency() {
        preferenceRepository.readSourceCurrencyCode()
            .launchIn(screenModelScope)
    }

    private fun fetchNewRates() {
        screenModelScope.launch {
            try {
                /** terminal operator to get the first emitted local cache */
                val localCache = mongoRepository.readCurrencyData().first()

                if(localCache.isSuccess()) {
                    if(!localCache.getSuccessData().isNullOrEmpty()) {
                        println("HomeViewModel: DATABASE IS FULL")
                        /** We should be null here as the check for isNullOrEmpty */
                        localCache.getSuccessData()?.let { listOfCurrencies ->
                            allCurrencies.addAll(listOfCurrencies)
                        }

                        /** Check if the data we are getting from the local cache is fresh */
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
        /** Fetch latest data from EP */
        val fetchData = currencyApiService.getLatestExchangeRates()

        if (fetchData.isSuccess()) {
            /** Clean up the cache before inputting duplicated data */
            mongoRepository.cleanUp()

            if (fetchData.getSuccessData() != null) {
                /** We should always be non-null here - complex expression cannot be evaluated
                 *  could use !! instead of ? */
                fetchData.getSuccessData()?.forEach { currencyModel ->
                    println("HomeViewModel: ADDING TO CACHE ${currencyModel.code}")
                    mongoRepository.insertCurrencyData(currencyModel)
                }
                println("HomeViewModel: UPDATING allCurrencies")
                fetchData.getSuccessData()?.let { listOfCurrencyModel ->
                    allCurrencies.clear()
                    allCurrencies.addAll(listOfCurrencyModel)
                }
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