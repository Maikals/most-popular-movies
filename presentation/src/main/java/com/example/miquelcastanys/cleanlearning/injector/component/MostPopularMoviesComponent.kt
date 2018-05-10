package com.example.miquelcastanys.cleanlearning.injector.component

import com.example.miquelcastanys.cleanlearning.injector.PerFragment
import com.example.miquelcastanys.cleanlearning.injector.module.BaseFragmentModule
import com.example.miquelcastanys.cleanlearning.injector.module.MostPopularMoviesModule
import com.example.miquelcastanys.cleanlearning.view.mostPopularMovies.MostPopularMoviesFragment
import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [BaseFragmentModule::class, MostPopularMoviesModule::class])
interface MostPopularMoviesComponent {

    fun inject(mostPopularMoviesFragment: MostPopularMoviesFragment)

}