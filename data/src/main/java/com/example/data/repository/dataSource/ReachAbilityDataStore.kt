package com.example.data.repository.dataSource

import com.example.domain.entity.ReachAbilityEntity

interface ReachAbilityDataStore {
    suspend fun pingHealthBackOffice(): ReachAbilityEntity
}