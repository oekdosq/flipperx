package com.flipperx.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.semanticBoundaries.semantics
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.flipperx.mobile.ui.screens.DashboardScreen

enum class ToolCategory {
    RED_TEAM,
    BLUE_TEAM,
    WHITE_HAT,
    BLACK_HAT,
    WEB_VULN,
    HARDWARE,
    NETWORK_UTILS,
    HISTORY_REPORT
}

@Composable
fun ToolCategoryIndicator(selected: ToolCategory, onSelect: (ToolCategory) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0x111722)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.Center
    ) {
        ToolCategory.values().forEach { category ->
            val isSelected = selected == category
            Button(
                onClick = { onSelect(category) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Color(0xFF8A00) else Color.Transparent,
                    contentColor = if (isSelected) Color(0x080B12) else Color(0xA0A8B5)
                ),
                elevation = ButtonElevation.None
            ) {
                Text(
                    text = when (category) {
                        ToolCategory.RED_TEAM -> "Red Team"
                        ToolCategory.BLUE_TEAM -> "Blue Team"
                        ToolCategory.WHITE_HAT -> "White Hat"
                        ToolCategory.BLACK_HAT -> "Black Hat"
                        ToolCategory.WEB_VULN -> "Web Vuln"
                        ToolCategory.HARDWARE -> "Hardware"
                        ToolCategory.NETWORK_UTILS -> "Network Utils"
                        ToolCategory.HISTORY_REPORT -> "History/Report"
                    },
                    color = if (isSelected) Color(0x080B12) else Color(0xA0A8B5),
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.h6
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    selectedCategory: MutableState<ToolCategory>,
    screenContent: @Composable (ToolCategory) -> Unit
) {
    Scaffold(
        bottomBar = {
            ToolCategoryIndicator(
                selected = selectedCategory.value,
                onSelect = { selectedCategory.value = it }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x080B12)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterStart
        ) {
            ToolCategoryIndicator(
                selected = selectedCategory.value,
                onSelect = { selectedCategory.value = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            screenContent(selectedCategory.value)
        }
    }
}

@Composable
fun DashboardScreenHost(context: Context) {
    val selectedCategory by remember { mutableStateOf(ToolCategory.RED_TEAM) }

    MainScreen(
        selectedCategory = selectedCategory,
        screenContent = { category ->
            when (category) {
                ToolCategory.RED_TEAM -> RedTeamScreen(context)
                ToolCategory.BLUE_TEAM -> BlueTeamScreen(context)
                ToolCategory.WHITE_HAT -> WhiteHatScreen(context)
                ToolCategory.BLACK_HAT -> BlackHatScreen(context)
                ToolCategory.WEB_VULN -> WebVulnScreen(context)
                ToolCategory.HARDWARE -> HardwareScreen(context)
                ToolCategory.NETWORK_UTILS -> NetworkUtilsScreen(context)
                ToolCategory.HISTORY_REPORT -> HistoryReportScreen(context)
            }
        }
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DashboardScreenHost(this)
        }
    }
}