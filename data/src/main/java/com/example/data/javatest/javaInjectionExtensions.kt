package com.example.data.javatest

import org.koin.java.standalone.KoinJavaComponent

fun <T : Any> inject(param: Class<T>): T {
    return KoinJavaComponent.inject(param).value
}

