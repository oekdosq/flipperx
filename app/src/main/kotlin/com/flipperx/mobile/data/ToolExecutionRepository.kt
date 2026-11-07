package com.flipperx.mobile.data

import android.content.Context

class ToolExecutionRepository(private val dao: ToolExecutionDao) {

    fun insert(result: ToolExecutionResult) {
        // Use coroutine context in ViewModel, but this is a simple wrapper
        // In real app, this would be called from a ViewModel with coroutine context
        // For now, we'll just provide the method signature
    }

    fun getAllResults(): LiveData<List<ToolExecutionResult>> {
        return dao.getAll()
    }

    fun getByToolName(toolName: String): LiveData<List<ToolExecutionResult>> {
        return dao.getByToolName(toolName)
    }
}