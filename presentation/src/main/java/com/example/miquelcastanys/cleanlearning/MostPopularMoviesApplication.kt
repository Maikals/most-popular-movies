package com.example.miquelcastanys.cleanlearning

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.example.miquelcastanys.cleanlearning.injector.component.ApplicationComponent
import com.example.miquelcastanys.cleanlearning.injector.component.DaggerApplicationComponent
import com.example.miquelcastanys.cleanlearning.injector.module.ApplicationModule
import com.example.miquelcastanys.cleanlearning.view.base.ReachAbilityManager
import javax.inject.Inject


class MostPopularMoviesApplication : MultiDexApplication() {

    @Inject
    lateinit var reachAbilityManager: ReachAbilityManager

    companion object {
        lateinit var instance: MostPopularMoviesApplication
        @JvmStatic
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        applicationComponent.inject(this)

        reachAbilityManager.registerReceiver(this)

        reachAbilityManager.vueHost= ("192.168.1.20")
        reachAbilityManager.fuseHost =("192.168.1.151")
        reachAbilityManager.checkVueReachAbleEnabled = true
        reachAbilityManager.checkFuseReachAbleEnabled = false
        reachAbilityManager.startTimerVUE()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}