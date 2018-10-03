package com.example.miquelcastanys.cleanlearning.view.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.CountDownTimer
import com.example.domain.base.Log
import com.example.domain.entity.EmptyParams
import com.example.domain.entity.InternetAddressParams
import com.example.domain.interactor.CheckDevicesReachAbilityUseCase
import com.example.domain.interactor.CheckInternetConnectionUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReachAbilityManager @Inject constructor(private val useCaseBackEnd: CheckInternetConnectionUseCase,
                                              private val useCaseDevice: CheckDevicesReachAbilityUseCase) : BroadcastReceiver() {

    private val timer: CountDownTimer

    companion object {
        private const val PERIOD = Long.MAX_VALUE
        private const val COUNT_DOWN_TIMER = 2000L
        private const val TAG: String = "ReachAbilityManager"
    }

    init {

        /**
         * This logic is pending to be approved and tested.
         * It checks the devices reach ability every 2 seconds
         * */
        timer = object : CountDownTimer(PERIOD, COUNT_DOWN_TIMER) {
            override fun onTick(millisUntilFinished: Long) {
                if (checkVueReachAbleEnabled) {
                    checkReachAbilityVUE()
                }
            }

            override fun onFinish() = Unit
        }
    }

    /**BackOffice Address with its listener and its block setter*/
    private var isBackOfficeReachable: Boolean = false
    private lateinit var backOfficeReachabilityListener: (Boolean) -> Unit

    /**
     * @param host the url to check or evaluate the back office reach ability
     * @param block function to set and later on answer the state changes
     * */
    fun setBackOfficeReachAbleListener(block: (Boolean) -> Unit) {
        backOfficeReachabilityListener = block
    }

    /**First device UDP logic Address with its listener and its block setter
     * VUE
     * */
    private val BROADCAST_PORT_VUE = 49153
    var vueHost = ""
        set(value) {
            field = value
            vueDevice.host = value
        }

    private var vueDevice = InternetAddressParams(vueHost, BROADCAST_PORT_VUE)
    private lateinit var vueReachabilityListener: (Boolean) -> Unit
    var checkVueReachAbleEnabled = false //boolean to enable disable checking the host

    /**
     * @param host the url to check or evaluate the VUE reach ability
     * @param block function to set and later on answer the state changes
     * */
    fun setReachAbleListenerVUE(block: (Boolean) -> Unit) {
        vueReachabilityListener = block
    }

    /**Second device UDP logic Address with its listener and its block setter
     * FUSE
     * */
    private val BROADCAST_PORT_FUSE = 8888
    var fuseHost = ""
        set(value) {
            field = value
            fuseDevice.host = value
        }
    private var fuseDevice = InternetAddressParams("", BROADCAST_PORT_FUSE)
    private lateinit var fuseReachabilityListener: (Boolean) -> Unit
    var checkFuseReachAbleEnabled = false//boolean to enable disable checking the host

    /**
     * @param host the url to check or evaluate the FUSE reach ability
     * @param block function to set and later on answer the state changes
     * */
    fun setReachAbleListenerFUSE(block: (Boolean) -> Unit) {
        fuseReachabilityListener = block
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
        val networkInfo = extras?.getParcelable<NetworkInfo>("networkInfo")

        if (networkInfo?.detailedState == NetworkInfo.State.CONNECTED) {
            if (!isNetworkStateConnected) {
                isNetworkStateConnected = true
                checkBackOfficeReachAbility()
                checkReachAbilityFUSE()
            }
        } else if (networkInfo?.detailedState == NetworkInfo.State.DISCONNECTED) {
            if (isNetworkStateConnected) {
                setNotReachAbleVariables()
                broadcastOffToListeners()
            }
            isNetworkStateConnected = false
        }
    }

    private fun setNotReachAbleVariables() {
        isBackOfficeReachable = false
        vueDevice.isReachAble = false
        fuseDevice.isReachAble = false
    }

    private fun broadcastOffToListeners() {

        backOfficeReachabilityListener(false)

        if (checkVueReachAbleEnabled) {
            vueReachabilityListener(false)
        }

        if (checkFuseReachAbleEnabled) {
            fuseReachabilityListener(false)
        }
    }

    /**
     * Through the use case checks the reachability of the BO and using the block function calls back to
     * the the BaseActivity.
     * Will only inform in changes, not if the same state as previous is received.
     * */
    private fun checkBackOfficeReachAbility() {

        useCaseBackEnd.execute(EmptyParams(), {

            if (it.result != isBackOfficeReachable) {
                isBackOfficeReachable = it.result
                backOfficeReachabilityListener(isBackOfficeReachable)
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
    fun checkReachAbilityVUE() {

        useCaseDevice.execute(vueDevice, {

            if (it.result != vueDevice.isReachAble) {
                vueDevice.isReachAble = it.result
                vueReachabilityListener(vueDevice.isReachAble)
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
    fun checkReachAbilityFUSE() {

        useCaseDevice.execute(fuseDevice, {
            if (it.result != fuseDevice.isReachAble) {
                fuseDevice.isReachAble = it.result
                fuseReachabilityListener(fuseDevice.isReachAble)
            }

        }, {
            //do nothing
        })
    }


    fun startTimerVUE() {
        Log.d(TAG, "timer VUE reach ability started.")
        timer.start()
    }

    fun stopTimerVUE() {
        Log.d(TAG, "timer VUE reach ability stopped.")
        timer.cancel()
    }

}