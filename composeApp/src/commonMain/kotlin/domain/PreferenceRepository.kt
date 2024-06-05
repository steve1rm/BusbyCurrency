package domain

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    val eventChannel: Flow<Boolean>

    suspend fun saveLastUpdated(lastUpdated: String)
    suspend fun isDataFresh(currentTimeStamp: Long): Boolean
}