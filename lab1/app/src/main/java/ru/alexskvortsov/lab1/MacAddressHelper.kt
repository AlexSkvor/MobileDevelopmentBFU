package ru.alexskvortsov.lab1

import java.net.NetworkInterface

fun getMacAddress(): String {
    try {
        val networkInterface = NetworkInterface.getNetworkInterfaces()
            .toList()
            .asSequence()
            .filter { it.name.equals("wlan0", ignoreCase = true) }
            .first()

        val macBytes: ByteArray = networkInterface.hardwareAddress ?: return ""

        val res1 = StringBuilder()
        macBytes.forEach { b ->
            res1.append(String.format("%02X:", b))
        }
        if (res1.isNotEmpty()) {
            res1.deleteCharAt(res1.length - 1)
        }
        return res1.toString()

    } catch (ex: Exception) {
    }
    return "02:00:00:00:00:00"
}