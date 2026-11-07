package com.flipperx.mobile.core

object ToolRegistry {
    private val tools = mapOf(
        ToolId.PORT_SCANNER to ToolDefinition(
            ToolId.PORT_SCANNER, "Port Scanner", ToolCategory.RED_TEAM,
            "Scan TCP ports on local target for open services.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.DNS_LOOKUP to ToolDefinition(
            ToolId.DNS_LOOKUP, "DNS Lookup", ToolCategory.RED_TEAM,
            "Resolve A, AAAA, MX, TXT records for target domains.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.IP_INFO to ToolDefinition(
            ToolId.IP_INFO, "IP Info & Geo", ToolCategory.RED_TEAM,
            "Inspect IP network interface info and routing.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.NETWORK_DISCOVERY to ToolDefinition(
            ToolId.NETWORK_DISCOVERY, "Network Discovery", ToolCategory.RED_TEAM,
            "Discover active hosts on local subnet.", listOf(android.Manifest.permission.ACCESS_NETWORK_STATE), null, ToolStatus.READY
        ),
        ToolId.NETWORK_MONITOR to ToolDefinition(
            ToolId.NETWORK_MONITOR, "Network Monitor", ToolCategory.BLUE_TEAM,
            "Monitor active connectivity and traffic counters.", listOf(android.Manifest.permission.ACCESS_NETWORK_STATE), null, ToolStatus.READY
        ),
        ToolId.ROGUE_AP to ToolDefinition(
            ToolId.ROGUE_AP, "Rogue AP Detector", ToolCategory.BLUE_TEAM,
            "Detect rogue access points and insecure Wi-Fi signals.", listOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_WIFI_STATE), null, ToolStatus.READY
        ),
        ToolId.DNS_SECURITY to ToolDefinition(
            ToolId.DNS_SECURITY, "DNS Security Check", ToolCategory.BLUE_TEAM,
            "Audit DNS configuration and query response security.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.HTTP_HEADERS to ToolDefinition(
            ToolId.HTTP_HEADERS, "HTTP Headers Inspector", ToolCategory.WHITE_HAT,
            "Inspect HTTP response headers and security flags.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.HTTP_SEC_HEADERS to ToolDefinition(
            ToolId.HTTP_SEC_HEADERS, "Security Headers Audit", ToolCategory.WHITE_HAT,
            "Audit HSTS, CSP, X-Frame-Options headers.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.JWT_INSPECTOR to ToolDefinition(
            ToolId.JWT_INSPECTOR, "JWT Inspector", ToolCategory.WHITE_HAT,
            "Decode and analyze JSON Web Tokens locally.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.BASE64 to ToolDefinition(
            ToolId.BASE64, "Base64 Utility", ToolCategory.WHITE_HAT,
            "Encode and decode Base64 strings securely.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.HASH_GEN to ToolDefinition(
            ToolId.HASH_GEN, "Hash Generator", ToolCategory.WHITE_HAT,
            "Generate SHA-256, MD5, SHA-1 checksums.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.HASH_ID to ToolDefinition(
            ToolId.HASH_ID, "Hash Identifier", ToolCategory.WHITE_HAT,
            "Identify hash algorithm types by structure.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.FILE_META to ToolDefinition(
            ToolId.FILE_META, "File Analyzer", ToolCategory.WHITE_HAT,
            "Analyze file metadata, magic bytes, and permissions.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.APK_PERMS to ToolDefinition(
            ToolId.APK_PERMS, "APK Permission Inspector", ToolCategory.WHITE_HAT,
            "Inspect APK permissions and manifest components.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.DEAUTH_ACTIVITY to ToolDefinition(
            ToolId.DEAUTH_ACTIVITY, "Wi-Fi Monitor (Deauth Watch)", ToolCategory.BLUE_TEAM,
            "Monitor Wi-Fi beacon frames and dropped packets (Defensive observation).", listOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_WIFI_STATE), null, ToolStatus.READY
        ),
        ToolId.PAYLOAD_TESTER to ToolDefinition(
            ToolId.PAYLOAD_TESTER, "Connection Tester", ToolCategory.WHITE_HAT,
            "Test socket connectivity and echo response.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.SECURITY_EVENT_LOG to ToolDefinition(
            ToolId.SECURITY_EVENT_LOG, "Security Event Log", ToolCategory.BLUE_TEAM,
            "Log and review local security telemetry events.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.SUSPICIOUS_CONN to ToolDefinition(
            ToolId.SUSPICIOUS_CONN, "Suspicious Connections", ToolCategory.BLUE_TEAM,
            "Inspect active socket connections for anomalies.", listOf(android.Manifest.permission.ACCESS_NETWORK_STATE), null, ToolStatus.READY
        ),
        ToolId.WIFI_INFO to ToolDefinition(
            ToolId.WIFI_INFO, "Wi-Fi Signal Inspector", ToolCategory.WIRELESS,
            "Analyze local Wi-Fi signal strength and link speed.", listOf(android.Manifest.permission.ACCESS_WIFI_STATE), null, ToolStatus.READY
        ),
        ToolId.WIFI_SIGNAL to ToolDefinition(
            ToolId.WIFI_SIGNAL, "Wi-Fi Channel Analyzer", ToolCategory.WIRELESS,
            "Scan available Wi-Fi channels and frequencies.", listOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_WIFI_STATE), null, ToolStatus.READY
        ),
        ToolId.SSL_TLS to ToolDefinition(
            ToolId.SSL_TLS, "SSL/TLS Certificate Checker", ToolCategory.WHITE_HAT,
            "Inspect TLS certificate chain and expiry for domain targets.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.VULN_ASSESS to ToolDefinition(
            ToolId.VULN_ASSESS, "Local Vulnerability Checklist", ToolCategory.WHITE_HAT,
            "Check device security posture against standard baselines.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.WEBSITE_SEC to ToolDefinition(
            ToolId.WEBSITE_SEC, "Web Security Audit", ToolCategory.WHITE_HAT,
            "Basic defensive assessment of web server configurations.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.BLE_SCANNER to ToolDefinition(
            ToolId.BLE_SCANNER, "BLE Device Scanner", ToolCategory.WIRELESS,
            "Scan nearby Bluetooth Low Energy beacons.", listOf(android.Manifest.permission.ACCESS_FINE_LOCATION), "Bluetooth LE", ToolStatus.READY
        ),
        ToolId.NFC_READER to ToolDefinition(
            ToolId.NFC_READER, "NFC Tag Inspector", ToolCategory.WIRELESS,
            "Inspect NFC tag metadata and NDEF records.", emptyList(), "NFC Hardware", ToolStatus.READY
        ),
        ToolId.SECURITY_REPORT to ToolDefinition(
            ToolId.SECURITY_REPORT, "Diagnostic Report Generator", ToolCategory.WHITE_HAT,
            "Export comprehensive diagnostic and security reports.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.WIRELESS_SEC to ToolDefinition(
            ToolId.WIRELESS_SEC, "Wireless Security Auditor", ToolCategory.WIRELESS,
            "Audit local wireless security encryption types.", listOf(android.Manifest.permission.ACCESS_WIFI_STATE), null, ToolStatus.READY
        ),
        ToolId.IR_CONTROL to ToolDefinition(
            ToolId.IR_CONTROL, "IR Blaster Diagnostic", ToolCategory.WIRELESS,
            "Check infrared hardware emitter capabilities.", emptyList(), "Consumer IR", ToolStatus.NEEDS_HARDWARE
        ),
        ToolId.PASSWORD_AUDITOR to ToolDefinition(
            ToolId.PASSWORD_AUDITOR, "Password Strength Auditor", ToolCategory.WHITE_HAT,
            "Evaluate password entropy and policy compliance locally.", emptyList(), null, ToolStatus.READY
        ),
        ToolId.NETWORK_DIAGNOSTICS to ToolDefinition(
            ToolId.NETWORK_DIAGNOSTICS, "Network Diagnostics Suite", ToolCategory.RED_TEAM,
            "Comprehensive ping, traceroute, and routing diagnostics.", emptyList(), null, ToolStatus.READY
        )
    )

    fun getAllTools(): List<ToolDefinition> = tools.values.toList()

    val allTools: List<ToolDefinition> = tools.values.toList()

    fun getTool(id: ToolId): ToolDefinition? = tools[id]

    fun getToolsByCategory(category: ToolCategory): List<ToolDefinition> =
        tools.values.filter { it.category == category }
}
