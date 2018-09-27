package com.example.domain.repository

import com.example.domain.entity.ReachAbilityCallParams
import com.example.domain.entity.ReachAbilityEntity

interface ReachAbility {
    fun checkHost(host: ReachAbilityCallParams) : ReachAbilityEntity
}
