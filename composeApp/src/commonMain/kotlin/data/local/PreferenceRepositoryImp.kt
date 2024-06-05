@file:OptIn(ExperimentalSettingsApi::class)

package data.local

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import domain.PreferenceRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class PreferenceRepositoryImp(
    settings: Settings
) : PreferenceRepository {

    companion object {
        private const val TIME_STAMP_KEY = "time_stamp:key"
    }

    private val flowSettings: FlowSettings = (settings as ObservableSettings).toFlowSettings()

    private val currencyRateChannel = Channel<Boolean>()
    override val currenyRateFlow = currencyRateChannel.receiveAsFlow()

    override suspend fun saveLastUpdated(lastUpdated: String) {
        val timeStampFromEndPoint = Instant.parse(lastUpdated).toEpochMilliseconds()

        flowSettings.putLong(
            key = TIME_STAMP_KEY,
            value = timeStampFromEndPoint)

        val currentTimestamp = Clock.System.now().toEpochMilliseconds()
        val isDataStillFresh = isCurrencyDataFresh(timeStampFromEndPoint, currentTimestamp)

        /** Send update to be observed that a new time stamp has been retrieved */
        currencyRateChannel.send(isDataStillFresh)
    }

    override suspend fun isDataFresh(currentTimestamp: Long): Boolean {
        val savedTimestamp = flowSettings.getLong(
            key = TIME_STAMP_KEY,
            defaultValue = 0L
        )

        return isCurrencyDataFresh(savedTimestamp, currentTimestamp)
    }

    private fun isCurrencyDataFresh(savedTimestamp: Long, currentTimestamp: Long): Boolean {
        return if (savedTimestamp != 0L) {
            val currentInstant = Instant.fromEpochMilliseconds(currentTimestamp)
            val savedInstant = Instant.fromEpochMilliseconds(savedTimestamp)

            val currentDateTime = currentInstant
                .toLocalDateTime(TimeZone.currentSystemDefault())
            val savedDateTime = savedInstant
                .toLocalDateTime(TimeZone.currentSystemDefault())

            val daysDifference = currentDateTime.date.dayOfYear - savedDateTime.date.dayOfYear

            daysDifference < 1
        }
        else {
            false
        }
    }
}
