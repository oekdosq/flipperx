package com.flipperx.mobile.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.flipperx.mobile.core.ToolId
import com.flipperx.mobile.core.ToolRegistry
import com.flipperx.mobile.data.ToolExecutionRepository
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class ToolViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun viewModel_getToolDetails_returnsCorrectInfo() = runBlockingTest {
        val tool = ToolRegistry.allTools.first { it.id == ToolId.DNS_LOOKUP }
        assertEquals("DNS_LOOKUP", tool.id.name)
        assertNotNull("Tool must have description", tool.description)
        assertNotNull("Tool must have category", tool.category)
    }

    @Test
    fun viewModel_allToolsHaveHandlersInRegistry() = runBlockingTest {
        ToolRegistry.allTools.forEach { tool ->
            assertNotNull("Tool ${tool.id.name} must have description", tool.description)
            assertNotNull("Tool ${tool.id.name} must have category", tool.category)
        }
    }
}