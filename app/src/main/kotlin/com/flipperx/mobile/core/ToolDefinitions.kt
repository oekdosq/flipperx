package com.flipperx.mobile.core

data class ToolDefinition(
    val id: ToolId,
    val name: String,
    val category: ToolCategory,
    val description: String,
    val requiredPermissions: List<String>,
    val requiredHardware: String?,
    val status: ToolStatus
)

enum class ToolStatus {
    READY,
    NEEDS_PERMISSION,
    NEEDS_HARDWARE,
    INCOMPLETE
}