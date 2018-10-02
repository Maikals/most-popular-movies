package com.example.miquelcastanys.cleanlearning

import android.app.Application
import com.example.miquelcastanys.cleanlearning.injector.component.ApplicationComponent
import com.example.miquelcastanys.cleanlearning.injector.component.DaggerApplicationComponent
import com.example.miquelcastanys.cleanlearning.injector.module.ApplicationModule
import com.example.miquelcastanys.cleanlearning.view.base.ReachAbilityManager


class MostPopularMoviesApplication : Application() {

    companion object {
        lateinit var instance: MostPopularMoviesApplication
        @JvmStatic
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()

        ReachAbilityManager.registerReceiver(this)
        ReachAbilityManager.checkFirstDeviceEnabled = true
        ReachAbilityManager.checkSecondDeviceEnabled = true

        ReachAbilityManager.launchVUEListener()

    }
}