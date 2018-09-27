package com.example.miquelcastanys.cleanlearning.injector.module

import com.example.miquelcastanys.cleanlearning.injector.PerFragment
import com.example.miquelcastanys.cleanlearning.view.mostPopularMovies.MostPopularMoviesFragment
import com.example.miquelcastanys.cleanlearning.view.mostPopularMovies.MostPopularMoviesView
import com.example.miquelcastanys.cleanlearning.view.newactivitydemo.ui.newactivitydemo.NewActivityDemoFragment
import dagger.Module
import dagger.Provides

@Module
class MostPopularMoviesNewModule(private val mostPopularMoviesFragment: NewActivityDemoFragment) {

}

