package com.example.domain.repository

import com.example.domain.entity.ReachAbilityCallParams
import com.example.domain.entity.ReachAbilityEntity

interface ReachAbility {
    fun checkBackEndHost(internetAddress: ReachAbilityCallParams): ReachAbilityEntity
}
