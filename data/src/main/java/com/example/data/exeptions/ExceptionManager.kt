package com.example.data.exeptions

import android.util.Log
import com.example.domain.exceptions.CustomException
import com.example.domain.exceptions.ExceptionType

object ExceptionManager {

    private const val TAG = "ExceptionManager"

    fun manageError(exception: CustomException): String {
        Log.d(TAG, exception.message, exception)

        //this has to retrieve the message from database
        return when (exception.exceptionType) {
            ExceptionType.CONNECTION_ERROR -> "CONNECTION ERROR"
            ExceptionType.UNDEFINED -> "UNDEFINED ERROR"
            else -> "UNDEFINED ERROR"
        }
    }
}