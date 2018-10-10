package com.example.miquelcastanys.cleanlearning

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.example.data.reachability.ReachAbilityManager
import com.example.miquelcastanys.cleanlearning.koinjector.generalModules
import org.koin.android.ext.android.startKoin
import org.koin.standalone.KoinComponent


class MostPopularMoviesApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, generalModules)

        ReachAbilityManager.registerReceiver(this)

        ReachAbilityManager.apply {
            vueHost = "192.168.1.20"
            fuseHost = ("192.168.1.151")
            checkVueReachAbleEnabled = false
            checkFuseReachAbleEnabled = false
            startTimerVUE()
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}