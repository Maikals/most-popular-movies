package com.example.domain.repository

interface ReachAbility {
    fun checkHost(host: ArrayList<String>) : Map<String,Boolean>
}
