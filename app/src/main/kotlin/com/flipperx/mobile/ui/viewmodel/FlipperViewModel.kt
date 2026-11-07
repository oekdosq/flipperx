package com.flipperx.mobile.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.flipperx.mobile.core.*
import com.flipperx.mobile.data.AppDatabase
import com.flipperx.mobile.data.ToolExecutionResult
import com.flipperx.mobile.data.ToolExecutionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FlipperViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ToolExecutionRepository
    private val _executionState = MutableStateFlow<Map<ToolId, String>>(emptyMap())
    val executionState: StateFlow<Map<ToolId, String>> = _executionState.asStateFlow()

    init {
        val dao = AppDatabase.getDatabase(application).toolExecutionDao()
        repository = ToolExecutionRepository(dao)
    }

    fun executeTool(toolId: ToolId, input: String) {
        viewModelScope.launch {
            val context = getApplication<Application>()
            val result = ExecutionManager.executeSafe(toolId.name, input) {
                RealToolHandlers.execute(context, toolId, input)
            }
            val output = result.getOrElse { "Error: ${it.localizedMessage}" }
            
            _executionState.value = _executionState.value + (toolId to output)

            // Save to Room DB (sanitized)
            try {
                val sanitizedInput = ExecutionManager.sanitizeInput(input)
                repository.insertResult(
                    ToolExecutionResult(
                        toolName = toolId.name,
                        input = sanitizedInput,
                        output = output,
                        timestamp = System.currentTimeMillis(),
                        isSuccess = result.isSuccess
                    )
                )
            } catch (e: Exception) {
                // DB log failure fallback
            }
        }
    }
}
