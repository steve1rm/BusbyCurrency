package di

import com.russhwolf.settings.Settings
import data.local.MongoRepositoryImp
import data.local.PreferenceRepositoryImp
import data.remote.api.CurrencyApiServiceImp
import domain.CurrencyApiService
import domain.MongoRepository
import domain.PreferenceRepository
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    singleOf(::Settings)

    singleOf(::CurrencyApiServiceImp).bind<CurrencyApiService>()

    singleOf(::PreferenceRepositoryImp).bind<PreferenceRepository>()

    singleOf(::MongoRepositoryImp).bind<MongoRepository>()
}