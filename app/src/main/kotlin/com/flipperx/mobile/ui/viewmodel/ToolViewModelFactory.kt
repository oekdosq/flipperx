package com.flipperx.mobile.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.flipperx.mobile.data.AppDatabase
import com.flipperx.mobile.data.ToolExecutionRepository

class ToolViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    private val database = AppDatabase.getDatabase(application)
    private val repository = database.toolExecutionDao().also { /* repository instance */ }

    // We'll need to instantiate ToolExecutionRepository properly
    private val toolRepository = com.flipperx.mobile.data.ToolExecutionRepository(database.toolExecutionDao())

    // Create tool-specific ViewModels
    fun getNetworkToolsViewModel(): NetworkToolsViewModel {
        return NetworkToolsViewModel(application)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NetworkToolsViewModel::class.java)) {
            return NetworkToolsViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}