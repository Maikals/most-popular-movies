package com.example.data.reachability

import com.example.domain.repository.ReachAbility
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject

class ReachAbilityImpl @Inject constructor() : ReachAbility {

    private lateinit var socket: Socket
    private val TAG = "ReachAbilityImpl"

    override fun checkHost(host: ArrayList<String>): Map<String, Boolean> =
            host.map {
                it to isConnected(it, 443)
            }.toMap()


    fun isConnected(host: String, port: Int): Boolean {
        socket = Socket()
        var isConnected: Boolean
        try {

            socket.connect(InetSocketAddress(host, port), 500)
            isConnected = socket.isConnected
        } catch (e: IOException) {
            isConnected = false
           // println(TAG + " Could not close the socket: " + e.printStackTrace())
        } finally {
            try {
                socket.close()
            } catch (e: IOException) {
                println(TAG + "Could not close the socket: " + e.printStackTrace())
            }
        }

        return isConnected

    }
}