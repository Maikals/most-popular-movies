package com.example.domain.entity

class InternetAddressParams(var host: String, val port: Int = 443, var isReachAble: Boolean = false) : BaseParams()