// Top-level build file
plugins {
    id("com.android.application") version "8.7.2" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.13" apply false
    id("org.jetbrains.kotlin.jvm") version "2.0.0" apply false
}

ext {
    composeBom = "2024.10.01"
    roomVersion = "2.7.2"
    kotlinVersion = "2.0.0"
}
