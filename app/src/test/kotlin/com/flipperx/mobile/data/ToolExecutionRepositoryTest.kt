package com.flipperx.mobile.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.flipperx.mobile.core.ToolExecutionResult
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class ToolExecutionRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun repository_insertAndRetrieve() = runBlockingTest {
        val db = AppDatabase.createInMemoryDatabase()
        val repo = ToolExecutionRepository(db)
        
        val result = ToolExecutionResult(
            toolId = "DNS_LOOKUP",
            input = "example.com",
            output = "93.184.216.34",
            timestamp = System.currentTimeMillis(),
            success = true
        )
        
        repo.insert(result)
        
        val all = repo.getAllResults().first()
        assertEquals(1, all.size)
        assertEquals("DNS_LOOKUP", all[0].toolId)
        assertEquals("example.com", all[0].input)
        assertEquals("93.184.216.34", all[0].output)
        assertTrue(all[0].success)
        
        db.close()
    }

    @Test
    fun repository_clearAll() = runBlockingTest {
        val db = AppDatabase.createInMemoryDatabase()
        val repo = ToolExecutionRepository(db)
        
        repo.insert(ToolExecutionResult("TEST", "in", "out", System.currentTimeMillis(), true))
        repo.clearAll()
        
        val all = repo.getAllResults().first()
        assertTrue("Database should be empty after clear", all.isEmpty())
        
        db.close()
    }
}