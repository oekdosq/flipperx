package com.flipperx.mobile.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.util.Date

@Entity(tableName = "tool_execution_results")
data class ToolExecutionResult(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "tool_name")
    val toolName: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "input")
    val input: String,
    @ColumnInfo(name = "output")
    val output: String,
    @ColumnInfo(name = "status")
    val status: String, // "Confirmed", "Potential", "NotVulnerable", "Inconclusive"
    @ColumnInfo(name = "timestamp")
    val timestamp: Date = Date()
)
