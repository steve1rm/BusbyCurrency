package data.local

import domain.MongoRepository
import domain.RequestState
import domain.model.CurrencyModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MongoRepositoryImp : MongoRepository {
    private var realm: Realm? = null

    init {
        configureRealm()
    }

    override fun configureRealm() {
        if(realm == null || realm?.isClosed() == true) {
            val config = RealmConfiguration.Builder(
                schema = setOf(CurrencyModel::class)
            ).compactOnLaunch().build()

            realm = Realm.open(config)
        }
    }

    override suspend fun insertCurrencyData(currencyModel: CurrencyModel) {
        realm?.write {
            this.copyToRealm(currencyModel)
        }
    }

    override fun readCurrencyData(): Flow<RequestState<List<CurrencyModel>>> {
        val cachedCurrency = realm?.query(CurrencyModel::class)?.asFlow()

        return cachedCurrency?.map { currencies ->
            RequestState.Success(data = currencies.list)
        }
            ?: flow {
                RequestState.Failure(message = "Realm incorrectly configured")
            }
    }

    override suspend fun cleanUp() {
        realm?.write {
            val currencyCollection = this.query(CurrencyModel::class)
            delete(currencyCollection)
        }
    }
}