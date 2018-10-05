package com.example.miquelcastanys.cleanlearning.injector.component

import com.example.miquelcastanys.cleanlearning.injector.PerFragment
import com.example.miquelcastanys.cleanlearning.injector.module.BaseFragmentModule
import com.example.miquelcastanys.cleanlearning.injector.module.MostPopularMoviesNewModule
import com.example.miquelcastanys.cleanlearning.view.newactivitydemo.ui.NewActivityDemoFragment
import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [BaseFragmentModule::class, MostPopularMoviesNewModule::class])
interface MostPopularMoviesNewComponent {

    fun inject(mostPopularMoviesFragment: NewActivityDemoFragment)

}