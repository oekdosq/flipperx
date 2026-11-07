package com.flipperx.mobile.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.flipperx.mobile.data.NetworkTools
import com.flipperx.mobile.data.ToolExecutionResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkToolsViewModel(application: Application) : AndroidViewModel(application) {

    private val networkTools = NetworkTools(application)

    fun executeDnsLookup(domain: String): ToolExecutionResult {
        val result = networkTools.lookupDNS(domain)
        return ToolExecutionResult(
            toolName = "DnsLookup",
            category = "network",
            input = domain,
            output = result,
            status = "Confirmed",
            timestamp = java.util.Date()
        )
    }

    fun executeNetworkType(): ToolExecutionResult {
        val result = networkTools.getNetworkType()
        return ToolExecutionResult(
            toolName = "NetworkType",
            category = "network",
            input = "",
            output = result,
            status = "Confirmed",
            timestamp = java.util.Date()
        )
    }

    fun executePortScan(host: String, port: Int): ToolExecutionResult {
        val result = networkTools.scanPort(host, port)
        return ToolExecutionResult(
            toolName = "PortScan",
            category = "network",
            input = "$host:$port",
            output = result,
            status = "Confirmed",
            timestamp = java.util.Date()
        )
    }
}