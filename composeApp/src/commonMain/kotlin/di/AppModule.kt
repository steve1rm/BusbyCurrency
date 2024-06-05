package di

import com.russhwolf.settings.Settings
import data.remote.api.CurrencyApiServiceImp
import domain.CurrencyApiService
import domain.PreferenceRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import data.local.PreferenceRepositoryImp
import org.koin.core.context.startKoin

val appModule = module {

    singleOf(::Settings)

    singleOf(::CurrencyApiServiceImp).bind<CurrencyApiService>()

    singleOf(::PreferenceRepositoryImp).bind<PreferenceRepository>()
}

fun initializeKoin() {
    startKoin {
        modules(appModule, homeModule)
    }
}