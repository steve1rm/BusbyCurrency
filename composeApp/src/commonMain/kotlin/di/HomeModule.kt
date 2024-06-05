package di

import domain.CurrencyApiService
import domain.PreferenceRepository
import org.koin.dsl.module
import presentation.home.HomeViewModel

val homeModule = module {

    factory {
        HomeViewModel(
            preferenceRepository = get<PreferenceRepository>(),
            currencyApiService = get<CurrencyApiService>()
        )
    }
}
