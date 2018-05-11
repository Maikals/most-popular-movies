package com.example.miquelcastanys.cleanlearning.injector.module

import com.example.miquelcastanys.cleanlearning.injector.PerFragment
import com.example.miquelcastanys.cleanlearning.view.mostPopularMovies.MostPopularMoviesFragment
import com.example.miquelcastanys.cleanlearning.view.mostPopularMovies.MostPopularMoviesView
import dagger.Module
import dagger.Provides

@Module
class MostPopularMoviesModule(private val mostPopularMoviesFragment: MostPopularMoviesFragment) {

    @Provides
    @PerFragment
    fun provideMostPopularMoviesView(): MostPopularMoviesView {
        return mostPopularMoviesFragment
    }
}

