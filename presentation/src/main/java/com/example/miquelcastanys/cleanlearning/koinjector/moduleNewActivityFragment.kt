package com.example.miquelcastanys.cleanlearning.koinjector

import com.example.data.db.MoviesDAO
import com.example.data.db.RealmManager
import com.example.data.net.MovieServiceImpl
import com.example.data.net.interceptor.RequestInterceptor
import com.example.data.repository.MostPopularMoviesRepositoryImpl
import com.example.data.repository.dataSource.MostPopularDataStoreImpl
import com.example.data.repository.dataSource.MostPopularMoviesStore
import com.example.domain.interactor.GetMostPopularMoviesUseCaseCoRoutines
import com.example.domain.repository.MostPopularMoviesRepository
import com.example.miquelcastanys.cleanlearning.view.newactivitydemo.ui.NewActivityDemoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val newFragmentModule = module {
    viewModel { NewActivityDemoViewModel(get()) }
}


val moduleMovieService = module {
    single { RequestInterceptor() }
    single { MovieServiceImpl(get()) }
}

val mostPopularMoviesApiModule = module {

    single { RealmManager(androidContext()) }
    single { MoviesDAO(get()) }
    single<MostPopularMoviesStore> { MostPopularDataStoreImpl(get(), get()) }
    single<MostPopularMoviesRepository> { MostPopularMoviesRepositoryImpl(get()) }
    factory { GetMostPopularMoviesUseCaseCoRoutines(get()) }
    //factory { MostPopularNewModelFactory((get())) }
}

val generalModules = listOf(newFragmentModule, moduleMovieService, mostPopularMoviesApiModule)
