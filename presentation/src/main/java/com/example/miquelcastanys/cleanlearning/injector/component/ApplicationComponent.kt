
package com.example.miquelcastanys.cleanlearning.injector.component

import com.example.miquelcastanys.cleanlearning.injector.module.ApplicationModule
import com.example.miquelcastanys.cleanlearning.injector.module.BaseFragmentModule
import com.example.miquelcastanys.cleanlearning.injector.module.MostPopularMoviesModule
import com.example.miquelcastanys.cleanlearning.view.base.BaseActivity
import com.example.miquelcastanys.cleanlearning.view.mostPopularMovies.MostPopularMoviesFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(baseActivity: BaseActivity)

    fun plus(baseFragmentModule: BaseFragmentModule, mostPopularMoviesModule: MostPopularMoviesModule) : MostPopularMoviesComponent

}