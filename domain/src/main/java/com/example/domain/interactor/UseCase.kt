package com.example.domain.interactor

interface UseCase {

    interface Callback {
        fun onReceived()
        fun onError()
    }

    fun execute(callback: Callback)

}