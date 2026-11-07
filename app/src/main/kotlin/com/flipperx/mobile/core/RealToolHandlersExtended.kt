package com.flipperx.mobile.core

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import java.io.File

object RealToolHandlersExtended {

    fun performFileMeta(context: Context, filePath: String): String {
        return try {
            val file = File(filePath)
            if (!file.exists()) return "File not found: $filePath"
            
            val size = file.length()
            val lastModified = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())
                .format(java.util.Date(file.lastModified()))
            val isReadable = file.canRead()
            val isWritable = file.canWrite()
            val isExecutable = file.canExecute()
            val extension = file.extension.uppercase()
            
            // Magic bytes check (first 4 bytes)
            val magicBytes = if (file.length() >= 4) {
                file.inputStream().use { 
                    val buffer = ByteArray(4)
                    it.read(buffer)
                    buffer.joinToString(" ") { "%02X".format(it) }
                }
            } else "N/A"
            
            buildString {
                append("File Analysis Report\n")
                append("====================\n")
                append("Path: ${file.absolutePath}\n")
                append("Name: ${file.name}\n")
                append("Extension: $extension\n")
                append("Size: ${size} bytes (${String.format("%.2f", size / 1024.0)} KB)\n")
                append("Last Modified: $lastModified\n")
                append("Permissions:\n")
                append("  - Readable: $isReadable\n")
                append("  - Writable: $isWritable\n")
                append("  - Executable: $isExecutable\n")
                append("Magic Bytes: $magicBytes\n")
                append("\nNote: Full magic byte database lookup requires additional implementation.\n")
            }
        } catch (e: Exception) {
            "File Analysis Error: ${e.localizedMessage}"
        }
    }

    fun performApkPerms(context: Context, apkPath: String): String {
        return try {
            val pm = context.packageManager
            val info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_PERMISSIONS or PackageManager.GET_ACTIVITIES or PackageManager.GET_SERVICES or PackageManager.GET_RECEIVERS)
            
            if (info == null) return "Failed to parse APK or APK not found: $apkPath"
            
            buildString {
                append("APK Permission Analysis\n")
                append("=======================\n")
                append("Package: ${info.packageName}\n")
                append("Version: ${info.versionName} (${info.versionCode})\n")
                append("Min SDK: ${info.applicationInfo.minSdkVersion}\n")
                append("Target SDK: ${info.applicationInfo.targetSdkVersion}\n")
                append("\nDeclared Permissions:\n")
                
                info.requestedPermissions?.forEachIndexed { index, perm ->
                    val flags = info.requestedPermissionsFlags[index]
                    val required = if (flags and PackageManager.REQUESTED_PERMISSION_REQUIRED != 0) "REQUIRED" else "OPTIONAL"
                    append("  [$required] $perm\n")
                }
                
                append("\nComponents:\n")
                append("  Activities: ${info.activities?.size ?: 0}\n")
                append("  Services: ${info.services?.size ?: 0}\n")
                append("  Receivers: ${info.receivers?.size ?: 0}\n")
                append("\nNote: This is static analysis only. No code execution performed.\n")
            }
        } catch (e: PackageManager.NameNotFoundException) {
            "APK not found or inaccessible: $apkPath"
        } catch (e: Exception) {
            "APK Analysis Error: ${e.localizedMessage}"
        }
    }

    fun performVulnAssess(context: Context): String {
        return try {
            val checks = mutableListOf<String>()
            var score = 0
            val maxScore = 10
            
            // Check 1: Debuggable flag
            val isDebuggable = context.applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE != 0
            checks.add("[${if (!isDebuggable) "PASS" else "FAIL"}] App debuggable flag: ${if (isDebuggable) "ENABLED (Security Risk)" else "DISABLED"}")
            if (!isDebuggable) score++
            
            // Check 2: Allow Backup
            val allowBackup = context.applicationInfo.allowBackup
            checks.add("[${if (!allowBackup) "PASS" else "WARN"}] Backup enabled: ${if (allowBackup) "YES (Data may be extractable)" else "NO"}")
            if (!allowBackup) score++
            
            // Check 3: Cleartext Traffic
            val usesCleartext = context.applicationInfo.usesCleartextTraffic
            checks.add("[${if (!usesCleartext) "PASS" else "WARN"}] Cleartext traffic allowed: ${if (usesCleartext) "YES" else "NO"}")
            if (!usesCleartext) score++
            
            // Check 4: Android Version
            val sdkLevel = Build.VERSION.SDK_INT
            checks.add("[${if (sdkLevel >= 26) "PASS" else "FAIL"}] Android version: ${Build.VERSION.RELEASE} (SDK $sdkLevel)")
            if (sdkLevel >= 26) score++
            
            // Check 5: Lock Screen Security (API 23+)
            if (Build.VERSION.SDK_INT >= 23) {
                val keyguard = context.getSystemService(Context.KEYGUARD_SERVICE) as android.app.KeyguardManager
                val isSecure = keyguard.isDeviceSecure
                checks.add("[${if (isSecure) "PASS" else "FAIL"}] Device lock screen: ${if (isSecure) "SECURED" else "NOT SECURED"}")
                if (isSecure) score++
            }
            
            // Check 6: Developer Options (USB Debugging detection limited without root)
            checks.add("[SKIP] USB Debugging status: Requires ADB or root access")
            
            // Check 7: Unknown Sources
            if (Build.VERSION.SDK_INT >= 26) {
                val unknownSources = context.packageManager.canRequestPackageInstalls()
                checks.add("[${if (!unknownSources) "PASS" else "WARN"}] Install unknown apps permission: ${if (unknownSources) "GRANTED" else "DENIED"}")
                if (!unknownSources) score++
            }
            
            // Check 8: Mock Location
            val mockLocation = android.provider.Settings.Secure.getInt(
                context.contentResolver, 
                android.provider.Settings.Secure.ALLOW_MOCK_LOCATION, 
                0
            ) == 1
            checks.add("[${if (!mockLocation) "PASS" else "FAIL"}] Mock location enabled: ${if (mockLocation) "YES" else "NO"}")
            if (!mockLocation) score++
            
            // Check 9: ADB Enabled (requires Settings.Global, may need permission)
            checks.add("[SKIP] ADB enabled status: Requires WRITE_SECURE_SETTINGS")
            
            // Check 10: Root Detection (basic)
            val isRooted = detectRoot()
            checks.add("[${if (!isRooted) "PASS" else "FAIL"}] Root access: ${if (isRooted) "DETECTED" else "NOT DETECTED"}")
            if (!isRooted) score++
            
            buildString {
                append("Local Vulnerability Assessment\n")
                append("==============================\n")
                append("Device: ${Build.MODEL} (${Build.DEVICE})\n")
                append("Android: ${Build.VERSION.RELEASE} (SDK $sdkLevel)\n\n")
                append("Security Checklist:\n")
                checks.forEach { append("$it\n") }
                append("\nScore: $score / $maxScore\n")
                append("Rating: ${when {\n                    score >= 8 -> "GOOD"\n                    score >= 5 -> "MODERATE"\n                    else -> "POOR"\n                }}\n")
                append("\nNote: This is a basic client-side assessment. Comprehensive audit requires server-side and network analysis.\n")
            }
        } catch (e: Exception) {
            "Vulnerability Assessment Error: ${e.localizedMessage}"
        }
    }
    
    private fun detectRoot(): Boolean {
        // Basic root detection (not comprehensive)
        val paths = listOf("/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su")
        return paths.any { File(it).exists() } || 
               try {
                   Runtime.getRuntime().exec(arrayOf("su", "-c", "id")).waitFor() == 0
               } catch (e: Exception) {
                   false
               }
    }
}