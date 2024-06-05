package presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.CurrencyApiService
import domain.PreferenceRepository
import domain.model.RateStatus
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class HomeViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val currencyApiService: CurrencyApiService
) : ScreenModel {

    var ratesStatus by mutableStateOf(RateStatus.Idle)
        private set

    init {
        preferenceRepository.eventChannel.onEach { isFresh ->
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
                val results = currencyApiService.getLatestExchangeRates()
//                println(results.isSuccess())
            }
            catch(exception: Exception) {
                println(exception.message)
            }
        }
    }

    override fun onDispose() {
        super.onDispose()
        println("onDispose for ${HomeViewModel::class.simpleName}")
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