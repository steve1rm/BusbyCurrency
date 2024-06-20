package presentation.home

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
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

    var sourceCurrency by mutableStateOf<RequestState<CurrencyModel>>(RequestState.Idle)
        private set

    var targetCurrency by mutableStateOf<RequestState<CurrencyModel>>(RequestState.Idle)
        private set

    var allCurrencies = mutableStateListOf<CurrencyModel>()
        private set

    init {
        /** Observe the rates time a request is made */
        preferenceRepository.currencyRateFlow.onEach { isFresh ->
            ratesStatus = if(isFresh) {
                println("HomeViewModel currencyRateFlow observed ${RateStatus.Fresh.name}")
                RateStatus.Fresh
            }
            else {
                println("HomeViewModel currencyRateFlow observed ${RateStatus.Stale.name}")
                RateStatus.Stale
            }
        }.launchIn(screenModelScope)

        mongoRepository.readCurrencyData().onEach { listOfCurrencies ->
            println("HomeViewModel DB Local observed ${listOfCurrencies.getSuccessData()?.count()}")
            allCurrencies.clear()

            if(listOfCurrencies.getSuccessData()?.isNotEmpty() == true) {
                listOfCurrencies.getSuccessData()?.let { currencies ->
                    allCurrencies.addAll(currencies)
                }
            }
        }.launchIn(screenModelScope)

        fetchNewRates()
        readSourceCurrency()
        readTargetCurrency()
    }

    fun homeEvents(events: HomeEvents) {
        when(events) {
            HomeEvents.RefreshRates -> {
                fetchNewRates()
            }
            HomeEvents.SwitchCurrency -> {
                switchCurrency()
            }
            is HomeEvents.saveSourceCurrencyCode -> {
                saveSourceCurrencyCode(events.currencyCode.name)
            }
            is HomeEvents.saveTargetCurrencyCode -> {
                saveTargetCurrencyCode(events.currencyCode.name)
            }
        }
    }

    private fun switchCurrency() {
        val tempSourceCurrency = sourceCurrency
        val tempTargetCurrency = targetCurrency

        sourceCurrency = tempTargetCurrency
        targetCurrency = tempSourceCurrency
    }

    private fun saveSourceCurrencyCode(currencyCode: String) {
        screenModelScope.launch(Dispatchers.IO) {
            preferenceRepository.saveSourceCurrencyCode(currencyCode)
        }
    }

    private fun saveTargetCurrencyCode(currencyCode: String) {
        screenModelScope.launch {
            preferenceRepository.saveTargetCurrencyCode(currencyCode)
        }
    }

    private fun readSourceCurrency() {
        screenModelScope.launch {
            preferenceRepository.readSourceCurrencyCode().collectLatest { currencyCode ->
                val selectedCurrency = allCurrencies.find { currencyModel ->
                    currencyCode.name == currencyModel.code
                }

                sourceCurrency = if(selectedCurrency != null) {
                    RequestState.Success(data = selectedCurrency)
                }
                else {
                    RequestState.Failure(message = "Could not find the selected country currency for source")
                }
            }
        }
    }

    private fun readTargetCurrency() {
        screenModelScope.launch {
            preferenceRepository.readTargetCurrencyCode().collectLatest { currencyCode ->
                val selectedCurrency = allCurrencies.find { currencyModel ->
                    currencyModel.code == currencyCode.name
                }

                targetCurrency = if(selectedCurrency != null) {
                    RequestState.Success(data = selectedCurrency)
                }
                else {
                    RequestState.Failure(message = "Could not find the selected country currency for target")
                }
            }
        }
    }

    private fun fetchNewRates() {
        screenModelScope.launch {
            try {
                /** terminal operator to get the first emitted local cache */
                val localCache = mongoRepository.readCurrencyData().first()

                if(localCache.isSuccess()) {
                    if(!localCache.getSuccessData().isNullOrEmpty()) {
                        println("HomeViewModel: DATABASE IS FULL")
                        /** We shouldn't be null here as the check for isNullOrEmpty */
                        localCache.getSuccessData()?.let { listOfCurrencies ->
                            allCurrencies.clear()
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
                /** TODO Should be getting the data from the DB and not from the Network
                 *  Change to get this from the DB by observing changes to it */
              /*  fetchData.getSuccessData()?.let { listOfCurrencyModel ->
                    allCurrencies.clear()
                    allCurrencies.addAll(listOfCurrencyModel)
                }*/
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