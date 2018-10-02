package com.example.miquelcastanys.cleanlearning

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.example.miquelcastanys.cleanlearning.injector.component.ApplicationComponent
import com.example.miquelcastanys.cleanlearning.injector.component.DaggerApplicationComponent
import com.example.miquelcastanys.cleanlearning.injector.module.ApplicationModule
import com.example.miquelcastanys.cleanlearning.view.base.ReachAbilityManager


class MostPopularMoviesApplication : MultiDexApplication() {

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

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}