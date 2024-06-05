@file:OptIn(ExperimentalSettingsApi::class)

package data.local

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import domain.PreferenceRepository
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

    override suspend fun saveLastUpdated(lastUpdated: String) {
        flowSettings.putLong(
            key = TIME_STAMP_KEY,
            value = Instant.parse(lastUpdated).toEpochMilliseconds())
    }

    override suspend fun isDataFresh(currentTimeStamp: Long): Boolean {
        val savedTimeStamp = flowSettings.getLong(
            key = TIME_STAMP_KEY,
            defaultValue = 0L)

        return if(savedTimeStamp != 0L) {
            val currentInstant = Instant.fromEpochMilliseconds(currentTimeStamp)
            val savedInstant = Instant.fromEpochMilliseconds(savedTimeStamp)

            val currentDateTime = currentInstant.toLocalDateTime(TimeZone.currentSystemDefault())
            val savedDateTime = savedInstant.toLocalDateTime(TimeZone.currentSystemDefault())

            val currentDay = currentDateTime.date.toEpochDays()
            val savedDay = savedDateTime.date.toEpochDays()

            val difference = currentDay - savedDay

            difference <= 1
        }
        else {
            return false
        }
    }
}