package domain

interface PreferenceRepository {
    suspend fun saveLastUpdated(lastUpdated: String)
    suspend fun isDataFresh(currentTimeStamp: Long): Boolean
}