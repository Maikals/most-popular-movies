package com.example.domain.interactor

import com.example.domain.base.BaseCoroutineUseCase
import com.example.domain.entity.ReachAbilityCallParams
import com.example.domain.repository.ReachAbility
import javax.inject.Inject

class CheckInternetConnectionUseCase @Inject constructor(private val reachAbility: ReachAbility):
         BaseCoroutineUseCase<Map<String,Boolean>, ReachAbilityCallParams>() {
    override fun buildRepoCall(params: ReachAbilityCallParams): Map<String,Boolean> = reachAbility.checkHost(params.host)
}
