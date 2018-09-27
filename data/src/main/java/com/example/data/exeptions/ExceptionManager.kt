package com.example.data.exeptions

import android.util.Log
import com.example.domain.exceptions.CustomException
import com.example.domain.exceptions.ExceptionType

object ExceptionManager {

    private val TAG = "ExceptionManager"
    lateinit var onErrorReceived: (String) -> Unit

    fun manageError(exception: CustomException) {
        var viewMessage = ""
        Log.d(TAG, exception.message ?: "ERROR")

        //this has to retrieve the message from database
        when (exception.exceptionType) {
            ExceptionType.CONNECTION_ERROR -> viewMessage = "CONNECTION ERROR"
            ExceptionType.UNDEFINED -> viewMessage = "UNDEFINED ERROR"
        }
        //TODO THIS NEEDS TO USE A ACTIVITY TAG TO AVOID BEING CALLED IN THE BASE ACTIVITY WHEN IT HASN'T BEEN CALLED BY THE SAME ORIGINAL VIEW
        onErrorReceived(viewMessage)
    }
}