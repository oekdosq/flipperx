package com.flipperx.mobile.ui.viewmodel

import android.content.Context
import com.flipperx.mobile.data.NetworkTools
import com.flipperx.mobile.data.ToolExecutionResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DnsLookupTool(private val context: Context) {

    private val networkTools = NetworkTools(context)

    suspend fun execute(domain: String): ToolExecutionResult {
        val result = networkTools.lookupDNS(domain)
        return ToolExecutionResult(
            toolName = "DnsLookup",
            category = "network",
            input = domain,
            output = result ?: "No IP found",
            status = "Confirmed",
            timestamp = java.util.Date()
        )
    }
}