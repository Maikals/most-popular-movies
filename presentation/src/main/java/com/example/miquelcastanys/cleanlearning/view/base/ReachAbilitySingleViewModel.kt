package com.example.miquelcastanys.cleanlearning.view.base

import android.arch.lifecycle.MutableLiveData
import android.os.CountDownTimer
import com.example.domain.entity.ReachAbilityCallParams
import com.example.domain.interactor.CheckInternetConnectionUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReachAbilitySingleViewModel @Inject constructor() {

    val BACK_OFFICE_HOST = "vetscanvue.abaxis.com"
    val DEFAULT_PORT = 443

    val isBackOfficeReachable = MutableLiveData<Boolean>().apply { value = true }
    val isVUEReachable = MutableLiveData<Boolean>().apply { value = true }

    @Inject
    lateinit var useCase: CheckInternetConnectionUseCase

    init {
        launchReachAbilityTimer()
    }

    //192.168.1.101

    private fun launchReachAbilityTimer() {
        val period = Long.MAX_VALUE
        val countDownInterval = 2000L

        object : CountDownTimer(period, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                useCase.execute(ReachAbilityCallParams(arrayListOf("192.168.1.101", BACK_OFFICE_HOST))) {

                    it.forEach {
                        when (it.key) {
                            "192.168.1.101" -> {
                                if (it.value != isVUEReachable.value)
                                    isVUEReachable.postValue(it.value)
                            }
                            BACK_OFFICE_HOST -> {
                                if (it.value != isBackOfficeReachable.value)
                                    isBackOfficeReachable.postValue(it.value)
                            }
                        }

                    }

                }
            }

            override fun onFinish() {
            }
        }.start()

    }


}