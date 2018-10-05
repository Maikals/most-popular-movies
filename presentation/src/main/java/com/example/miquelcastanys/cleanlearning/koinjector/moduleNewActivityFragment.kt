package com.example.miquelcastanys.cleanlearning.koinjector

import com.example.data.db.RealmHelper
import com.example.data.net.MovieServiceImpl
import com.example.data.net.interceptor.RequestInterceptor
import com.example.data.repository.MostPopularMoviesRepositoryImpl
import com.example.data.repository.dataSource.MostPopularDataStoreImpl
import com.example.data.repository.dataSource.MostPopularMoviesStore
import com.example.domain.interactor.GetMostPopularMoviesUseCaseCoRoutines
import com.example.domain.repository.MostPopularMoviesRepository
import com.example.miquelcastanys.cleanlearning.view.newactivitydemo.ui.NewActivityDemoFragment
import com.example.miquelcastanys.cleanlearning.view.newactivitydemo.ui.NewActivityDemoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val newFragmentModule = module {

    scope("org.scope") { NewActivityDemoFragment() }
    viewModel { NewActivityDemoViewModel(get()) }
}


val moduleApplication = module {

    module {

    }

}

val moduleMovieService = module {

    single { RequestInterceptor() }
    single { MovieServiceImpl(get()) }
}

val mostPopularMoviesApiModule = module {

    single<MostPopularMoviesStore> { MostPopularDataStoreImpl(get(), RealmHelper(androidContext())) }
    single<MostPopularMoviesRepository> { MostPopularMoviesRepositoryImpl(get()) }
    factory { GetMostPopularMoviesUseCaseCoRoutines(get()) }
    //factory { MostPopularNewModelFactory((get())) }
}