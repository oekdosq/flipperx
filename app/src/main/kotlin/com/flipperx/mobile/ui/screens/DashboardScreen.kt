package com.flipperx.mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.os.Build
import android.app.ActivityManager
import android.content.Context
import java.io.File

@Composable
fun DashboardScreen(context: Context) {
    val backgroundColor = Color(0x080B12)
    val primaryColor = Color(0xFFFF8A00)
    val surfaceColor = Color(0x111722)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "FLIPPERX",
                color = primaryColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "FULL EDITION v1.0.0",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        item {
            InfoCard(title = "Device Info", items = listOf(
                "Model" to Build.MODEL,
                "Manufacturer" to Build.MANUFACTURER,
                "Hardware" to Build.HARDWARE,
                "Android Version" to Build.VERSION.RELEASE
            ), primaryColor = primaryColor, surfaceColor = surfaceColor)
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            val actManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val memInfo = ActivityManager.MemoryInfo()
            actManager.getMemoryInfo(memInfo)
            
            InfoCard(title = "System Stats", items = listOf(
                "RAM Total" to "${memInfo.totalMem / 1024 / 1024} MB",
                "RAM Avail" to "${memInfo.availMem / 1024 / 1024} MB",
                "Storage Total" to "${File(context.filesDir.absolutePath).totalSpace / 1024 / 1024 / 1024} GB"
            ), primaryColor = primaryColor, surfaceColor = surfaceColor)
        }
    }
}

@Composable
fun InfoCard(title: String, items: List<Pair<String, String>>, primaryColor: Color, surfaceColor: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, color = primaryColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            items.forEach { (label, value) ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = label, color = Color(0xFFA0A8B5), fontSize = 14.sp)
                    Text(text = value, color = Color.White, fontSize = 14.sp)
                }
            }
        }
    }
}
