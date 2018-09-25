package com.example.domain.repository

import com.example.domain.entity.InternetAddress

interface ReachAbility {
    fun checkHost(host: ArrayList<InternetAddress>) : ArrayList<InternetAddress>
}
