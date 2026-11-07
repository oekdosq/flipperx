package com.flipperx.mobile.core

import org.junit.Assert.*
import org.junit.Test

class ToolRegistryTest {

    @Test
    fun toolRegistry_has31Tools() {
        val count = ToolRegistry.allTools.size
        assertEquals("Should have 31 ToolIds (30 original + NETWORK_DIAGNOSTICS)", 31, count)
    }

    @Test
    fun toolRegistry_containsOriginal30Tools() {
        val original30 = setOf(
            ToolId.PORT_SCANNER, ToolId.DNS_LOOKUP, ToolId.IP_INFO, ToolId.NETWORK_DISCOVERY,
            ToolId.NETWORK_MONITOR, ToolId.ROGUE_AP, ToolId.DNS_SECURITY, ToolId.HTTP_HEADERS,
            ToolId.HTTP_SEC_HEADERS, ToolId.JWT_INSPECTOR, ToolId.BASE64, ToolId.HASH_GEN,
            ToolId.HASH_ID, ToolId.FILE_META, ToolId.APK_PERMS, ToolId.DEAUTH_ACTIVITY,
            ToolId.PAYLOAD_TESTER, ToolId.SECURITY_EVENT_LOG, ToolId.SUSPICIOUS_CONN,
            ToolId.WIFI_INFO, ToolId.WIFI_SIGNAL, ToolId.SSL_TLS, ToolId.VULN_ASSESS,
            ToolId.WEBSITE_SEC, ToolId.BLE_SCANNER, ToolId.NFC_READER, ToolId.SECURITY_REPORT,
            ToolId.WIRELESS_SEC, ToolId.IR_CONTROL, ToolId.PASSWORD_AUDITOR
        )
        val all = ToolRegistry.allTools.map { it.id }.toSet()
        assertTrue("All 30 original tools must be present", original30.all { it in all })
    }

    @Test
    fun toolRegistry_containsNetworkDiagnostics() {
        val all = ToolRegistry.allTools.map { it.id }.toSet()
        assertTrue("NETWORK_DIAGNOSTICS must be present", ToolId.NETWORK_DIAGNOSTICS in all)
    }

    @Test
    fun toolRegistry_noDuplicates() {
        val ids = ToolRegistry.allTools.map { it.id }
        assertEquals("No duplicate ToolIds", ids.size, ids.toSet().size)
    }

    @Test
    fun toolRegistry_allHaveCategory() {
        ToolRegistry.allTools.forEach { tool ->
            assertNotNull("Tool ${tool.id.name} must have category", tool.category)
        }
    }
}