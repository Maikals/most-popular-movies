package com.example.miquelcastanys.cleanlearning.koinjector

import com.example.data.db.MoviesDAO
import com.example.data.db.RealmManager
import com.example.data.net.MovieServiceAdapter
import com.example.data.net.SearchMoviesServiceAdapter
import com.example.data.net.interceptor.ReachAbilityRequestInterceptor
import com.example.data.net.interceptor.RequestInterceptor
import com.example.data.reachability.ReachAbilityDevicesImpl
import com.example.data.reachability.ReachAbilityImpl
import com.example.data.reachability.ReachAbilityServiceAdapter
import com.example.data.repository.MostPopularMoviesRepositoryImpl
import com.example.data.repository.SearchMoviesRepositoryImpl
import com.example.data.repository.dataSource.*
import com.example.domain.interactor.*
import com.example.domain.repository.MostPopularMoviesRepository
import com.example.domain.repository.ReachAbility
import com.example.domain.repository.ReachAbilityDevices
import com.example.domain.repository.SearchMoviesRepository
import com.example.miquelcastanys.cleanlearning.presenter.MostPopularMoviesPresenter
import com.example.miquelcastanys.cleanlearning.view.mostPopularMovies.MostPopularMoviesViewModel
import com.example.miquelcastanys.cleanlearning.view.newactivitydemo.ui.NewActivityDemoViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val viewModels = module {
    viewModel { NewActivityDemoViewModel(useCase = get()) }
    viewModel { MostPopularMoviesViewModel(localUseCase = get(), useCaseAllMovies = get()) }
    factory { MostPopularMoviesPresenter() }
}

//val applicationModule = module {
//
//    factory { androidContext() }
//}

val movieServiceModule = module {
    single { RequestInterceptor() }
    single { MovieServiceAdapter(authInterceptor = get()) }
    single { SearchMoviesServiceAdapter(authInterceptor = get()) }
}

val realmModule = module {
    single { RealmManager }
}

val mostPopularMoviesApiModule = module {
    single { MoviesDAO(realmManager = get()) }
    single<MostPopularMoviesStore> { MostPopularDataStoreImpl(moviesDAO = get(), mostPopularMoviesService = get()) }
    single<MostPopularMoviesRepository> { MostPopularMoviesRepositoryImpl(mostPopularMoviesStore = get()) }

    single<SearchMoviesDataStore> { SearchMoviesDataStoreImpl(searchMoviesService = get()) }
    single<SearchMoviesRepository> { SearchMoviesRepositoryImpl(searchMoviesDataStore = get()) }
}

val useCasesModule = module {
    factory { GetMostPopularMoviesUseCase(mostPopularMoviesRepository = get()) }
    factory { GetSavedMoviesUseCase(mostPopularMoviesRepository = get()) }
    factory { GetSearchMoviesUseCase(searchMoviesRepository = get()) }
    factory { CheckInternetConnectionUseCase(reachAbility = get()) }
    factory { CheckDevicesReachAbilityUseCase(reachAbilityDevices = get()) }
}

val reachAbilityModule = module {
    single { ReachAbilityRequestInterceptor() }
    single { ReachAbilityServiceAdapter(reachAbilityRequestInterceptor = get()) }
    single<ReachAbilityDataStore> { ReachAbilityDataStoreImpl(reachAbilityServiceAdapter = get()) }
    single<ReachAbility> { ReachAbilityImpl(reachAbilityDataStore = get()) }
    single<ReachAbilityDevices> { ReachAbilityDevicesImpl() }
}

val generalModules = listOf(reachAbilityModule, realmModule, viewModels, movieServiceModule, mostPopularMoviesApiModule, useCasesModule)
