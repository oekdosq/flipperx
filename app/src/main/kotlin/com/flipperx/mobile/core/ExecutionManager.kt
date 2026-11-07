package com.flipperx.mobile.core

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.util.logging.Logger

object ExecutionManager {
    private val logger = Logger.getLogger("ExecutionManager")

    suspend fun <T> executeSafe(
        toolName: String,
        input: String,
        timeoutMs: Long = 10000L,
        block: suspend () -> T
    ): Result<T> = withContext(Dispatchers.IO) {
        val sanitizedInput = sanitizeInput(input)
        logger.info("Executing tool: $toolName with sanitized input length: ${sanitizedInput.length}")
        try {
            val result = withTimeout(timeoutMs) {
                block()
            }
            Result.success(result)
        } catch (e: CancellationException) {
            logger.warning("Tool $toolName cancelled")
            Result.failure(e)
        } catch (e: Exception) {
            logger.severe("Tool $toolName failed: ${e.localizedMessage}")
            Result.failure(e)
        }
    }

    fun sanitizeInput(input: String): String {
        // Remove potential sensitive tokens or credentials before logging
        return input.replace(Regex("(?i)(password|token|secret|auth)=[^&\\s]+"), "$1=REDACTED")
    }
}
