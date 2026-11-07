package com.flipperx.mobile.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.net.InetAddress
import java.net.NetworkInterface

class NetworkTools(private val context: Context) {

    fun scanPort(host: String, port: Int, timeout: Int = 1000): String {
        return try {
            val socket = java.net.Socket()
            socket.connect(java.net.InetSocketAddress(host, port), timeout)
            socket.close()
            "Port $port: OPEN"
        } catch (e: Exception) {
            "Port $port: CLOSED"
        }
    }

    fun lookupDNS(domain: String): String {
        return try {
            val addresses = InetAddress.getAllByName(domain)
            if (addresses.isEmpty()) "No records found"
            else addresses.joinToString("\n") { it.hostAddress ?: "Unknown" }
        } catch (e: Exception) {
            "Lookup failed: ${e.message}"
        }
    }

    fun getNetworkType(): String {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return "Disconnected"
        val caps = cm.getNetworkCapabilities(network) ?: return "Unknown"

        return when {
            caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "WiFi"
            caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Cellular"
            caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "Ethernet"
            caps.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> "Bluetooth"
            else -> "Other"
        }
    }

    fun getDeviceIP(): String {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as? android.net.wifi.WifiManager
        val wifiInfo = wifiManager?.connectionInfo
        val wifiIp = wifiInfo?.ipAddress?.let {
            if (it != 0) {
                val ip = it.toInt()
                "${ip and 0xff}.${(ip shr 8) and 0xff}.${(ip shr 16) and 0xff}.${(ip shr 24) and 0xff}"
            } else null
        }
        if (!wifiIp.isNullOrEmpty()) return "WiFi: $wifiIp"

        return try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val intf = interfaces.nextElement()
                if (intf.isLoopback || !intf.isUp) continue
                val addrs = intf.inetAddresses
                while (addrs.hasMoreElements()) {
                    val addr = addrs.nextElement()
                    if (!addr.isLoopbackAddress && addr is java.net.Inet4Address) {
                        return "Interface: ${addr.hostAddress}"
                    }
                }
            }
            "No active IPv4 found"
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }

    fun checkIntegrity(): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            "Version: ${packageInfo.versionName} | Last Update: ${packageInfo.lastUpdateTime}"
        } catch (e: Exception) { "Error: ${e.message}" }
    }
}