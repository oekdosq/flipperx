package com.flipperx.mobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flipperx.mobile.core.*

@Composable
fun ToolDetailScreen(
    toolId: ToolId,
    onBack: () -> Unit,
    onExecute: (String) -> Unit
) {
    val tool = ToolRegistry.getTool(toolId) ?: return
    var input by remember { mutableStateOf("") }
    var executionResult by remember { mutableStateOf<String?>(null) }
    var isRunning by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF080B12))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onBack, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF121824))) {
                Text("< Back", color = Color(0xFF00E5FF))
            }
            Text(
                text = tool.category.name,
                color = Color(0xFFFF8A00),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = tool.name,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = tool.description,
            color = Color(0xFF94A3B8),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF121824)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = "Status: ${tool.status}", color = Color(0xFF00E5FF), fontSize = 12.sp)
                if (tool.requiredHardware != null) {
                    Text(text = "Required Hardware: ${tool.requiredHardware}", color = Color(0xFFFF8A00), fontSize = 12.sp)
                }
                if (tool.requiredPermissions.isNotEmpty()) {
                    Text(text = "Permissions: ${tool.requiredPermissions.joinToString()}", color = Color(0xFF94A3B8), fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Target / Input (Sanitized)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFF8A00),
                unfocusedBorderColor = Color(0xFF94A3B8),
                focusedLabelColor = Color(0xFFFF8A00),
                unfocusedLabelColor = Color(0xFF94A3B8),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                isRunning = true
                ExecutionManager.executeSafe(tool.name, input) {
                    // Simulate execution or delegate to real engine
                    kotlinx.coroutines.delay(800)
                    "Executed successfully against target: ${ExecutionManager.sanitizeInput(input)}"
                }.let { res ->
                    executionResult = res.getOrElse { "Execution failed: ${it.localizedMessage}" }
                    isRunning = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8A00)),
            enabled = !isRunning
        ) {
            Text(if (isRunning) "Running Diagnostic..." else "Run Diagnostic / Tool", color = Color.Black, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (executionResult != null) {
            Text(text = "Execution Result:", color = Color(0xFF00E5FF), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF121824))
                    .padding(12.dp)
            ) {
                Text(text = executionResult!!, color = Color.White, fontSize = 13.sp)
            }
        }
    }
}
