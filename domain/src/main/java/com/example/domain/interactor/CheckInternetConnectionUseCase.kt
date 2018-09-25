package com.example.domain.interactor

import com.example.domain.base.BaseCoroutineUseCase
import com.example.domain.entity.InternetAddress
import com.example.domain.entity.ReachAbilityCallParams
import com.example.domain.repository.ReachAbility

class CheckInternetConnectionUseCase constructor(private val reachAbility: ReachAbility) :
        BaseCoroutineUseCase<ArrayList<InternetAddress>, ReachAbilityCallParams>() {
    override fun buildRepoCall(params: ReachAbilityCallParams): ArrayList<InternetAddress> = reachAbility.checkHost(params.host)
}
