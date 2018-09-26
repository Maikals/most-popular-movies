package com.example.miquelcastanys.cleanlearning.view.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import com.example.data.reachability.ReachAbilityImpl
import com.example.domain.entity.InternetAddress
import com.example.domain.entity.ReachAbilityCallParams
import com.example.domain.interactor.CheckInternetConnectionUseCase


object ReachAbilityManager : BroadcastReceiver() {

    private var useCase = CheckInternetConnectionUseCase(ReachAbilityImpl())

    private var primaryAddress = InternetAddress("")
    private lateinit var primaryHostBlock: (Boolean) -> Unit

    private var secondaryAddress = InternetAddress("")
    private lateinit var secondaryHostBlock: (Boolean) -> Unit
    var checkSecondaryHostEnabled = false

    fun initializeParams(primaryHost: String, secondaryHost: String, primaryPort: Int = 443,
                         secondaryPort: Int = 443) {
        this.primaryAddress = InternetAddress(primaryHost, primaryPort)
        this.secondaryAddress = InternetAddress(secondaryHost, secondaryPort)
    }

    fun setPrimaryHostListener(primaryHostBlock: (Boolean) -> Unit) {
        this.primaryHostBlock = primaryHostBlock
    }

    fun setSecondaryHostListener(secundaryHostBlock: (Boolean) -> Unit) {
        this.secondaryHostBlock = secundaryHostBlock
    }

    fun registerReceiver(context: Context?) {
        context?.registerReceiver(this, IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION))
    }

    private var isNetworkStateConnected: Boolean = false

    override fun onReceive(context: Context?, intent: Intent?) {
        val extras = intent?.extras
        if (extras != null) {
            val networkInfo = extras?.getParcelable<NetworkInfo>("networkInfo")
            if (networkInfo != null) {
                when (networkInfo.detailedState) {
                    NetworkInfo.State.CONNECTED -> {
                        if (!isNetworkStateConnected) {
                            isNetworkStateConnected = true
                            start()
                        }
                    }

                    NetworkInfo.State.DISCONNECTED -> {

                        if (isNetworkStateConnected) {

                            primaryAddress.isReachAble = false
                            secondaryAddress.isReachAble = false

                            primaryHostBlock(false)

                            if (checkSecondaryHostEnabled) {
                                secondaryHostBlock(false)
                            }
                        }

                        isNetworkStateConnected = false
                    }
                }
            }
        }
    }

    fun start() {

        useCase.execute(ReachAbilityCallParams(buildArrayFromHosts())) { arrayInternetAddress ->

            arrayInternetAddress.forEach {

                when (it.host) {

                    primaryAddress.host -> {
                        if (it.isReachAble != primaryAddress.isReachAble) {
                            primaryAddress.isReachAble = it.isReachAble
                            primaryHostBlock(primaryAddress.isReachAble)
                        }
                    }

                    secondaryAddress.host -> {
                        if (it.isReachAble != secondaryAddress.isReachAble) {
                            secondaryAddress.isReachAble = it.isReachAble
                            secondaryHostBlock(secondaryAddress.isReachAble)
                        }
                    }
                }

            }

        }


    }

    private fun buildArrayFromHosts(): ArrayList<InternetAddress> {
        val arrayListHost: ArrayList<InternetAddress> = arrayListOf()
        if (!primaryAddress.host.isEmpty()) {
            arrayListHost.add(primaryAddress)
        }
        if (!secondaryAddress.host.isEmpty() && checkSecondaryHostEnabled) {
            arrayListHost.add(secondaryAddress)
        }
        return arrayListHost
    }


}