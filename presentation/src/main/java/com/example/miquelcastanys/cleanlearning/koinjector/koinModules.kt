package com.example.miquelcastanys.cleanlearning.koinjector

import com.example.data.db.MoviesDAO
import com.example.data.db.RealmManager
import com.example.data.net.MovieServiceImpl
import com.example.data.net.interceptor.RequestInterceptor
import com.example.data.repository.MostPopularMoviesRepositoryImpl
import com.example.data.repository.dataSource.MostPopularDataStoreImpl
import com.example.data.repository.dataSource.MostPopularMoviesStore
import com.example.domain.interactor.GetMostPopularMoviesUseCaseCoRoutines
import com.example.domain.interactor.GetSavedMoviesUseCase
import com.example.domain.repository.MostPopularMoviesRepository
import com.example.miquelcastanys.cleanlearning.view.mostPopularMovies.MostPopularMoviesViewModel
import com.example.miquelcastanys.cleanlearning.view.newactivitydemo.ui.NewActivityDemoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val viewModels = module {
    viewModel { NewActivityDemoViewModel(useCase = get(), useCaseRealm = get()) }
    viewModel { MostPopularMoviesViewModel(localUseCase = get(), useCase = get()) }
}

val movieServiceModule = module {
    single { RequestInterceptor() }
    single { MovieServiceImpl(authInterceptor = get()) }
}

val realmModule = module {
    single { RealmManager(androidContext()) }
}

val mostPopularMoviesApiModule = module {
    single { MoviesDAO(realmManager = get()) }
    single<MostPopularMoviesStore> { MostPopularDataStoreImpl(moviesDAO = get(), mostPopularMoviesService = get()) }
    single<MostPopularMoviesRepository> { MostPopularMoviesRepositoryImpl(mostPopularMoviesStore = get()) }
}

val useCasesModule = module {
    factory { GetMostPopularMoviesUseCaseCoRoutines(mostPopularMoviesRepository = get()) }
    factory { GetSavedMoviesUseCase(mostPopularMoviesRepository = get()) }
}

val generalModules = listOf(realmModule, viewModels, movieServiceModule, mostPopularMoviesApiModule, useCasesModule)
