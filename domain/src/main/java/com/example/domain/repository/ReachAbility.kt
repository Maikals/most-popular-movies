package com.example.domain.repository

import com.example.domain.entity.ReachAbilityEntity

interface ReachAbility {
    suspend fun pingHealthBackOffice(): ReachAbilityEntity
}
