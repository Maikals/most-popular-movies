package com.example.data.reachability

import com.example.domain.base.Log
import com.example.domain.entity.InternetAddressParams
import com.example.domain.entity.ReachAbilityEntity
import com.example.domain.repository.ReachAbilityDevices
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class ReachAbilityDevicesImpl : ReachAbilityDevices {

    companion object {
        private const val TAG = "ReachAbilityDevicesImpl"
    }

    override fun checkDevices(internetAddressParams: InternetAddressParams): ReachAbilityEntity = ReachAbilityEntity(checkDeviceReachAbleViaUDP(internetAddressParams))

    //VUE does not care about the message, but FUSE needs this exact message, so its "FUSE" and works with both devices.
    private val SEND_DATA = "FUSE".toByteArray()

    private fun checkDeviceReachAbleViaUDP(internetAddressParams: InternetAddressParams): Boolean {

        var isReachAble: Boolean
        val datagramSocket = DatagramSocket()

        try {
            datagramSocket.soTimeout = 3000

            val socketAddress = InetAddress.getByName(internetAddressParams.host)
            val sendPacket = DatagramPacket(SEND_DATA, SEND_DATA.size, socketAddress, internetAddressParams.port)
            datagramSocket.send(sendPacket)

            val buf = ByteArray(15000)

            val receivePacket = DatagramPacket(buf, buf.size)
            Log.d(TAG,"ReachAbilityDevices: Sending request packet to:  ${socketAddress.hostAddress};")
            datagramSocket.receive(receivePacket)

            isReachAble = !receivePacket.data.isEmpty()

            if (isReachAble) {
                Log.d(TAG,"UDP ESTABLISHED: ${internetAddressParams.host}")
            }

        } catch (e: Exception) {
            isReachAble = false
            Log.e(TAG,"UDP Message at ip ${internetAddressParams.host} is not responding. \nError: $e")
        } finally {
            datagramSocket.close()
        }
        return isReachAble
    }

}