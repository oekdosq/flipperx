package com.flipperx.mobile.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ToolExecutionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result: ToolExecutionResult): Long

    @Query("SELECT * FROM tool_execution_results ORDER BY timestamp DESC")
    fun getAll(): LiveData<List<ToolExecutionResult>>

    @Query("SELECT * FROM tool_execution_results WHERE tool_name = :toolName ORDER BY timestamp DESC")
    fun getByToolName(toolName: String): LiveData<List<ToolExecutionResult>>
}
