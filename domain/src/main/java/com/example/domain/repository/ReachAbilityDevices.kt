package com.example.domain.repository

import com.example.domain.entity.ReachAbilityDeviceCallParams
import com.example.domain.entity.ReachAbilityDeviceEntity

interface ReachAbilityDevices {
    fun checkDevices(params: ReachAbilityDeviceCallParams): ReachAbilityDeviceEntity
}