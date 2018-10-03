package com.example.domain.base


object Log {

    var DEBUG: Boolean = true
    fun d(TAG: String, s: String) {
        if (DEBUG)
            System.out.println("DEBUG:$TAG: $s")
    }

    fun w(TAG: String, s: String) {
        if (DEBUG)
            System.out.println("WARNING:$TAG: $s")
    }

    fun e(TAG: String, s: String) {
        if (DEBUG)
            System.err.println("ERROR:$TAG: $s")
    }

    fun e(TAG: String, s: String, throwable: Throwable) {
        if (DEBUG)
            System.err.println("ERROR:$TAG: $s: $throwable")
    }
}