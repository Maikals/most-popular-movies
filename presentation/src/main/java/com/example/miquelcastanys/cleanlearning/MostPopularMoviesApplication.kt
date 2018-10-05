package com.example.miquelcastanys.cleanlearning

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.example.miquelcastanys.cleanlearning.injector.component.ApplicationComponent
import com.example.miquelcastanys.cleanlearning.injector.component.DaggerApplicationComponent
import com.example.miquelcastanys.cleanlearning.injector.module.ApplicationModule
import com.example.miquelcastanys.cleanlearning.koinjector.moduleApplication
import com.example.miquelcastanys.cleanlearning.koinjector.moduleMovieService
import com.example.miquelcastanys.cleanlearning.koinjector.mostPopularMoviesApiModule
import com.example.miquelcastanys.cleanlearning.koinjector.newFragmentModule
import com.example.miquelcastanys.cleanlearning.view.base.ReachAbilityManager
import org.koin.android.ext.android.startKoin
import org.koin.standalone.KoinComponent
import javax.inject.Inject


class MostPopularMoviesApplication : MultiDexApplication(), KoinComponent {

    @Inject
    lateinit var reachAbilityManager: ReachAbilityManager

    companion object {
        lateinit var instance: MostPopularMoviesApplication
        @JvmStatic
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        bindModulesKoin()
        instance = this
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        applicationComponent.inject(this)

        reachAbilityManager.registerReceiver(this)

        reachAbilityManager.vueHost = ("192.168.1.20")
        reachAbilityManager.fuseHost = ("192.168.1.151")
        reachAbilityManager.checkVueReachAbleEnabled = true
        reachAbilityManager.checkFuseReachAbleEnabled = false
        reachAbilityManager.startTimerVUE()
    }

    private fun bindModulesKoin() {
        startKoin(this, listOf(moduleApplication, moduleMovieService, newFragmentModule, mostPopularMoviesApiModule))
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}