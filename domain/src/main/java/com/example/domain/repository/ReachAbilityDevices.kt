package com.example.domain.repository

import com.example.domain.entity.InternetAddressParams
import com.example.domain.entity.ReachAbilityEntity

interface ReachAbilityDevices {
    fun checkDevices(internetAddressParams: InternetAddressParams): ReachAbilityEntity
}