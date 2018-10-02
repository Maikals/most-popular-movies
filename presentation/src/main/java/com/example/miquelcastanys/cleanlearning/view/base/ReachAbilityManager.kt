package com.example.miquelcastanys.cleanlearning.view.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.CountDownTimer
import com.example.data.reachability.ReachAbilityDevicesImpl
import com.example.data.reachability.ReachAbilityImpl
import com.example.domain.entity.InternetAddress
import com.example.domain.entity.ReachAbilityCallParams
import com.example.domain.entity.ReachAbilityDeviceCallParams
import com.example.domain.interactor.CheckDevicesReachAbilityUseCase
import com.example.domain.interactor.CheckInternetConnectionUseCase


object ReachAbilityManager : BroadcastReceiver() {

    /**UseCases*/
    private var useCaseBackEnd = CheckInternetConnectionUseCase(ReachAbilityImpl())
    private var useCaseDevices = CheckDevicesReachAbilityUseCase(ReachAbilityDevicesImpl())

    /**BackOffice Address with its listener and its block setter*/
    private var backOfficeAddress = InternetAddress("")
    private lateinit var primaryHostListener: (Boolean) -> Unit

    /**
     * @param host the url to check or evaluate the back office reach ability
     * @param block function to set and later on answer the state changes
     * */
    fun setBackOfficeReachAbleListener(host: String, block: (Boolean) -> Unit) {
        backOfficeAddress = InternetAddress(host)
        primaryHostListener = block
    }

    /**First device UDP logic Address with its listener and its block setter
     * VUE
     * */
    private const val BROADCAST_PORT_VUE = 49153
    private var firstDevice = InternetAddress("")
    private lateinit var firstDeviceHostListener: (Boolean) -> Unit
    var checkFirstDeviceEnabled = false //boolean to enable disable checking the host

    /**
     * @param host the url to check or evaluate the VUE reach ability
     * @param block function to set and later on answer the state changes
     * */
    fun setFirstDeviceReachAbleListener(host: String, block: (Boolean) -> Unit) {
        firstDevice = InternetAddress(host, BROADCAST_PORT_VUE)
        firstDeviceHostListener = block
    }

    /**Second device UDP logic Address with its listener and its block setter
     * FUSE
     * */
    private const val BROADCAST_PORT_FUSE = 8888
    private var secondDevice = InternetAddress("")
    private lateinit var secondDeviceHostListener: (Boolean) -> Unit
    var checkSecondDeviceEnabled = false//boolean to enable disable checking the host

    /**
     * @param host the url to check or evaluate the FUSE reach ability
     * @param block function to set and later on answer the state changes
     * */
    fun setSecondDeviceReachAbleListener(host: String, block: (Boolean) -> Unit) {
        secondDevice = InternetAddress(host, BROADCAST_PORT_FUSE)
        secondDeviceHostListener = block
    }


    /**
     * @param context general Context to register the receiver
     * */
    fun registerReceiver(context: Context?) {
        context?.registerReceiver(this, IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION))
    }

    /**Var to evaluate if the network is connected or not*/
    private var isNetworkStateConnected: Boolean = false


    /**@Override method to receive connectivity changes from system Broadcast */
    override fun onReceive(context: Context?, intent: Intent?) {
        val extras = intent?.extras
        if (extras != null) {
            val networkInfo = extras?.getParcelable<NetworkInfo>("networkInfo")
            if (networkInfo != null) {


                when (networkInfo.detailedState) {
                    NetworkInfo.State.CONNECTED -> {
                        if (!isNetworkStateConnected) {
                            isNetworkStateConnected = true
                            checkHostBackOffice()
                            //checkDevicesArray() //possible call instead of the tick every X seconds.
                        }
                    }

                    NetworkInfo.State.DISCONNECTED -> {

                        if (isNetworkStateConnected) {
                            setNotReachAbleVariables()
                            broadcastOffToListeners()
                        }

                        isNetworkStateConnected = false
                    }
                }
            }
        }
    }

    private fun setNotReachAbleVariables() {
        backOfficeAddress.isReachAble = false
        firstDevice.isReachAble = false
        secondDevice.isReachAble = false
    }

    private fun broadcastOffToListeners() {

        primaryHostListener(false)

        if (checkFirstDeviceEnabled) {
            firstDeviceHostListener(false)
        }

        if (checkSecondDeviceEnabled) {
            secondDeviceHostListener(false)
        }
    }

    /**
     * Through the use case checks the reachability of the BO and using the block function calls back to
     * the the BaseActivity.
     * Will only inform in changes, not if the same state as previous is received.
     * */
    private fun checkHostBackOffice() {

        useCaseBackEnd.execute(ReachAbilityCallParams(InternetAddress(backOfficeAddress.host)), {

            if (it.host.isReachAble != backOfficeAddress.isReachAble) {
                backOfficeAddress.isReachAble = it.host.isReachAble
                primaryHostListener(backOfficeAddress.isReachAble)
            }

        }, {
            //do nothing
        })
    }

    /**
     * Through the use case checks the reachability of the Devices Array and using the block function calls back to
     * the the BaseActivity.
     * Will only inform in changes, not if the same state as previous is received for each devices/host
     * */
    fun checkDevicesArray() {

        useCaseDevices.execute(ReachAbilityDeviceCallParams(buildArrayFromHosts()), { entity ->
            entity.internetAddressArray.forEach {

                when (it.host) {

                    firstDevice.host -> {
                        if (it.isReachAble != firstDevice.isReachAble) {
                            firstDevice.isReachAble = it.isReachAble
                            firstDeviceHostListener(firstDevice.isReachAble)
                        }
                    }

                    secondDevice.host -> {
                        if (it.isReachAble != secondDevice.isReachAble) {
                            secondDevice.isReachAble = it.isReachAble
                            secondDeviceHostListener(secondDevice.isReachAble)
                        }
                    }
                }

            }

        }, {
            //do nothing
        })
    }

    /**
     * Build an array of host to send to the use case params of the Devices logic.
     * */
    private fun buildArrayFromHosts(): ArrayList<InternetAddress> {
        val arrayListHost: ArrayList<InternetAddress> = arrayListOf()
        if (!firstDevice.host.isEmpty() && checkFirstDeviceEnabled) {
            arrayListHost.add(firstDevice)
        }
        if (!secondDevice.host.isEmpty() && checkSecondDeviceEnabled) {
            arrayListHost.add(secondDevice)
        }
        return arrayListHost
    }


    /**
     * This logic is pending to be approved and tested.
     * It checks the devices reach ability every 2 seconds
     * */
    fun launchVUEListener() {
        val period = Long.MAX_VALUE
        val countDownInterval = 2000L

        object : CountDownTimer(period, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                checkDevicesArray()
            }

            override fun onFinish() = Unit
        }.start()
    }

}