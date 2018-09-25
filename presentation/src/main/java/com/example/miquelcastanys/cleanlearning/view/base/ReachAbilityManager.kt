package com.example.miquelcastanys.cleanlearning.view.base

import android.arch.lifecycle.MutableLiveData
import android.os.CountDownTimer
import com.example.data.reachability.ReachAbilityImpl
import com.example.domain.entity.InternetAddress
import com.example.domain.entity.ReachAbilityCallParams
import com.example.domain.interactor.CheckInternetConnectionUseCase

object ReachAbilityManager {

    private var useCase = CheckInternetConnectionUseCase(ReachAbilityImpl())

    var primaryAddress = InternetAddress("")
    val isPrimaryAddressReachAble: MutableLiveData<Boolean> = MutableLiveData()

    var secondaryAddress = InternetAddress("")
    val isSecondaryAddressReachAble: MutableLiveData<Boolean> = MutableLiveData()
    var checkSecondaryHostEnabled = false

    fun initializeParams(primaryHost: String, secondaryHost: String, primaryPort: Int = 443, secondaryPort: Int = 443) {
        this.primaryAddress = InternetAddress(primaryHost, primaryPort)
        this.secondaryAddress = InternetAddress(secondaryHost, secondaryPort)
        start()
    }

    fun start() {

        val period = Long.MAX_VALUE
        val countDownInterval = 2000L

        object : CountDownTimer(period, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {

                useCase.execute(ReachAbilityCallParams(buildArrayFromHosts())) { arrayInternetAddress ->

                    arrayInternetAddress.forEach {

                        when {
                            it.host == primaryAddress.host ->
                                when {
                                    it.isReachAble != isPrimaryAddressReachAble.value ->
                                        isPrimaryAddressReachAble.postValue(it.isReachAble)
                                }
                        }

                        when {
                            it.host == secondaryAddress.host ->
                                when {
                                    it.isReachAble != isSecondaryAddressReachAble.value ->
                                        isSecondaryAddressReachAble.postValue(it.isReachAble)
                                }
                        }
                    }

                }
            }

            override fun onFinish() = Unit
        }.start()

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