package com.flipperx.mobile.ui.screens

import android.content.Context
import android.net.wifi.ScanResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.flipperx.mobile.ui.viewmodel.NetworkToolsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextFieldDefaults

@Composable
fun RedTeamScreen(context: Context) {
    val networkToolsViewModel: NetworkToolsViewModel = viewModel(factory = ToolViewModelFactory(context))
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0x080B12)).padding(16.dp)
    ) {
        Text(text = "Red Team - Network Reconnaissance", color = Color(0xFF8A00), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        var input by remember { mutableStateOf("") }
        var output by remember { mutableStateOf("") }
        var status by remember { mutableStateOf("") }

        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text(text = "Target Domain/IP", color = Color(0xA0A8B5)) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedContainerColor = Color(0x111722),
                    unfocusedContainerColor = Color(0x111722),
                    focusedIndicatorColor = Color(0xFF8A00),
                    unfocusedIndicatorColor = Color(0xA0A8B5)
                ),
                modifier = Modifier.fillMaxWidth().background(Color(0x111722))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        status = "Processing..."
                        try {
                            val result = networkToolsViewModel.executeDnsLookup(input)
                            output = "DNS Lookup Result:\n${result.output}"
                            status = "Confirmed"
                        } catch (e: Exception) {
                            output = "Error: ${e.message}"
                            status = "Failed"
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A00), contentColor = Color(0x080B12)),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Run DNS Lookup", color = Color(0x080B12))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = output, color = Color(0xFFFFFF))
            Text(text = status, color = Color(0xA0A8B5))

            // Port Scan Demo
            var portInput by remember { mutableStateOf("") }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = portInput,
                onValueChange = { portInput = it },
                label = { Text(text = "Port", color = Color(0xA0A8B5)) },
                modifier = Modifier.fillMaxWidth().background(Color(0x111722))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        status = "Scanning..."
                        try {
                            val result = networkToolsViewModel.executePortScan(input, portInput.toIntOrNull() ?: 0)
                            output = "Scan Result: ${result.output}"
                            status = "Done"
                        } catch (e: Exception) {
                            output = "Err: ${e.message}"
                            status = "Failed"
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A00), contentColor = Color(0x080B12)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Scan Port")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = output, color = Color(0xFFFFFF))
            Text(text = status, color = Color(0xA0A8B5))
        }
    }
}

@Composable
fun BlueTeamScreen(context: Context) {
    val scanResults = remember { mutableStateListOf<ScanResult>() }
    var status by remember { mutableStateOf("Scanning...") }
    val wifiManager = context.applicationContext.getSystemService(android.content.Context.WIFI_SERVICE) as android.net.wifi.WifiManager

    LaunchedEffect(Unit) {
        val results = wifiManager.scanResults
        scanResults.clear()
        scanResults.addAll(results)
        status = if (results.any { it.capabilities.contains("Open") && it.level > -50 })
            "Potential Rogue AP detected" else "Network Clean"
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0x080B12)).padding(16.dp)) {
        Text("Blue Team - Rogue AP Detection", color = Color(0xFF8A00), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Status: $status", color = Color.White)
        LazyColumn {
            items(scanResults) { ap ->
                Text("AP: ${ap.SSID} | Level: ${ap.level} | Caps: ${ap.capabilities}", color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun WhiteHatScreen(context: Context) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0x080B12)).padding(16.dp)) {
        Text(text = "White Hat - Security Auditing", color = Color(0xFF8A00), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Feature: Integrity Check (Pending)", color = Color.White)
    }
}

@Composable
fun BlackHatScreen(context: Context) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0x080B12)).padding(16.dp)) {
        Text(text = "Black Hat - Lab", color = Color(0xFF8A00), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Feature: Payload Gen (Pending)", color = Color.White)
    }
}

@Composable
fun WebVulnScreen(context: Context) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0x080B12)).padding(16.dp)) {
        Text(text = "Web Vuln - Assessment", color = Color(0xFF8A00), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Feature: SQLi Scanner (Pending)", color = Color.White)
    }
}

@Composable
fun HardwareScreen(context: Context) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0x080B12)).padding(16.dp)) {
        Text(text = "Hardware - Wireless", color = Color(0xFF8A00), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Feature: BLE Scan (Pending)", color = Color.White)
    }
}

@Composable
fun NetworkUtilsScreen(context: Context) {
    val networkToolsViewModel: NetworkToolsViewModel = viewModel(factory = ToolViewModelFactory(context.applicationContext as android.app.Application))

    Column(modifier = Modifier.fillMaxSize().background(Color(0x080B12)).padding(16.dp)) {
        Text(text = "Network Utilities", color = Color(0xFF8A00), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* trigger IP check */ }) { Text("Get Local IP") }
        Text(text = "Current IP: ...", color = Color.White)
    }
}

@Composable
fun HistoryReportScreen(context: Context) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0x080B12)).padding(16.dp)) {
        Text(text = "History & Report", color = Color(0xFF8A00), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "No logs yet.", color = Color.White)
    }
}