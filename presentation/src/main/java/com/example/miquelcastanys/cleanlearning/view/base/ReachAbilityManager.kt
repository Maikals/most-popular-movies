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
    lateinit var primaryHostListener: (Boolean) -> Unit

    private var secondaryAddress = InternetAddress("")
    lateinit var secondaryHostListener: (Boolean) -> Unit
    var checkSecondaryHostEnabled = false

    fun initializeParams(primaryHost: String, secondaryHost: String, primaryPort: Int = 443,
                         secondaryPort: Int = 443) {
        this.primaryAddress = InternetAddress(primaryHost, primaryPort)
        this.secondaryAddress = InternetAddress(secondaryHost, secondaryPort)
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

                            primaryHostListener(false)

                            if (checkSecondaryHostEnabled) {
                                secondaryHostListener(false)
                            }
                        }

                        isNetworkStateConnected = false
                    }
                }
            }
        }
    }

    fun start() {

        useCase.execute(ReachAbilityCallParams(buildArrayFromHosts()), {
            it.host.forEach {

                when (it.host) {

                    primaryAddress.host -> {
                        if (it.isReachAble != primaryAddress.isReachAble) {
                            primaryAddress.isReachAble = it.isReachAble
                            primaryHostListener(primaryAddress.isReachAble)
                        }
                    }

                    secondaryAddress.host -> {
                        if (it.isReachAble != secondaryAddress.isReachAble) {
                            secondaryAddress.isReachAble = it.isReachAble
                            secondaryHostListener(secondaryAddress.isReachAble)
                        }
                    }
                }

            }

        }, {

        })
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