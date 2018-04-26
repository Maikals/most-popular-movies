package com.example.domain.repository

import com.example.domain.callback.Callback


interface Repository {
    fun get(callback: Callback)
}