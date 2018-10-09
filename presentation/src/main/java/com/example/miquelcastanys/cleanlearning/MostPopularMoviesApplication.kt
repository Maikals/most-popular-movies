package com.example.miquelcastanys.cleanlearning

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.example.miquelcastanys.cleanlearning.koinjector.generalModules
import com.example.data.reachability.ReachAbilityManager
import org.koin.android.ext.android.startKoin
import org.koin.standalone.KoinComponent


class MostPopularMoviesApplication : MultiDexApplication(), KoinComponent {

    companion object {
        lateinit var instance: MostPopularMoviesApplication
    }

    override fun onCreate() {
        super.onCreate()
        startKoin(this, generalModules)
        instance = this

        ReachAbilityManager.registerReceiver(this)

        //TODO try again a builder approach
        ReachAbilityManager.vueHost = ("192.168.1.20")
        ReachAbilityManager.fuseHost = ("192.168.1.151")
        ReachAbilityManager.checkVueReachAbleEnabled = true
        ReachAbilityManager.checkFuseReachAbleEnabled = false
        ReachAbilityManager.startTimerVUE()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}