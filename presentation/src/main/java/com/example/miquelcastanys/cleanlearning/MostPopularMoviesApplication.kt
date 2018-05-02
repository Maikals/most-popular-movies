
package com.example.miquelcastanys.cleanlearning

import android.app.Application
import com.example.miquelcastanys.cleanlearning.injector.component.ApplicationComponent
import com.example.miquelcastanys.cleanlearning.injector.component.DaggerApplicationComponent
import com.example.miquelcastanys.cleanlearning.injector.module.ApplicationModule


class MostPopularMoviesApplication : Application() {

    companion object {
        lateinit var instance: MostPopularMoviesApplication
        @JvmStatic
        lateinit var applicationModule: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        applicationModule = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
    }
}