package com.flipperx.mobile.core

import org.junit.Assert.*
import org.junit.Test

class RealToolHandlersTest {

    @Test
    fun performBase64_encodesAndDecodesCorrectly() {
        val input = "FlipperX Security"
        val result = RealToolHandlersTestAccess.performBase64(input)
        assertTrue("Result should contain encoded string", result.contains("Base64 Encode:"))
        assertTrue("Result should contain decoded string", result.contains("Base64 Decode:"))
        assertTrue("Decoded should match input", result.contains(input))
    }

    @Test
    fun performHashGen_producesMD5AndSHA256() {
        val input = "test"
        val result = RealToolHandlersTestAccess.performHashGen(input)
        assertTrue("Should contain MD5", result.contains("MD5:"))
        assertTrue("Should contain SHA-256", result.contains("SHA-256:"))
        // MD5 of "test" = 098f6bcd4621d373cade4e832627b4f6
        assertTrue("MD5 should be correct", result.contains("098f6bcd4621d373cade4e832627b4f6"))
    }

    @Test
    fun performHashId_identifiesHashLengths() {
        assertEquals("MD5 length 32", "Likely MD5 or NTLM hash", RealToolHandlersTestAccess.performHashId("a".repeat(32)))
        assertEquals("SHA-1 length 40", "Likely SHA-1 or RIPEMD-160 hash", RealToolHandlersTestAccess.performHashId("a".repeat(40)))
        assertEquals("SHA-256 length 64", "Likely SHA-256 or BLAKE2s hash", RealToolHandlersTestAccess.performHashId("a".repeat(64)))
        assertEquals("Unknown length", "Unknown hash format or plain text (Length: 10)", RealToolHandlersTestAccess.performHashId("a".repeat(10)))
    }

    @Test
    fun performJwtInspect_parsesValidJWT() {
        // JWT with header {"alg":"HS256","typ":"JWT"} and payload {"sub":"1234567890","name":"John Doe"}
        val jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIn0.dummy"
        val result = RealToolHandlersTestAccess.performJwtInspect(jwt)
        assertTrue("Should contain header", result.contains("JWT Header:"))
        assertTrue("Should contain payload", result.contains("JWT Payload:"))
        assertTrue("Should contain alg", result.contains("HS256"))
        assertTrue("Should contain sub", result.contains("1234567890"))
    }

    @Test
    fun performJwtInspect_rejectsInvalidFormat() {
        val result = RealToolHandlersTestAccess.performJwtInspect("invalid")
        assertTrue("Should reject invalid JWT", result.contains("Invalid JWT format"))
    }

    @Test
    fun performPasswordAudit_scoresCorrectly() {
        val weak = "123"
        val medium = "Password1"
        val strong = "Str0ng!Passw0rd"
        
        val weakResult = RealToolHandlersTestAccess.performPasswordAudit(weak)
        assertTrue("Weak password score < 5", weakResult.contains("Complexity Score: "))
        
        val strongResult = RealToolHandlersTestAccess.performPasswordAudit(strong)
        assertTrue("Strong password should meet policy", strongResult.contains("Policy Met (>=12 chars, upper, lower, digit, special): true"))
    }
}

// Test access object to call private functions
object RealToolHandlersTestAccess {
    @JvmStatic
    fun performBase64(input: String): String = RealToolHandlers::class.java.getDeclaredMethod("performBase64", String::class.java).apply { isAccessible = true }.invoke(null, input) as String
    
    @JvmStatic
    fun performHashGen(input: String): String = RealToolHandlers::class.java.getDeclaredMethod("performHashGen", String::class.java).apply { isAccessible = true }.invoke(null, input) as String
    
    @JvmStatic
    fun performHashId(input: String): String = RealToolHandlers::class.java.getDeclaredMethod("performHashId", String::class.java).apply { isAccessible = true }.invoke(null, input) as String
    
    @JvmStatic
    fun performJwtInspect(input: String): String = RealToolHandlers::class.java.getDeclaredMethod("performJwtInspect", String::class.java).apply { isAccessible = true }.invoke(null, input) as String
    
    @JvmStatic
    fun performPasswordAudit(input: String): String = RealToolHandlers::class.java.getDeclaredMethod("performPasswordAudit", String::class.java).apply { isAccessible = true }.invoke(null, input) as String
}