# FlipperX - Full Edition v1.0.0

## Repository Overview

This repository contains the **FlipperX Full Edition** (Version 1.0.0), a production‑ready security‑assessment framework for Android built on Kotlin and targeting **Android 16 (SDK 36)** with **Java 21** toolchain.

- **Primary Language:** Kotlin
- **Target SDK:** 36 (Android 16)
- **Min SDK:** 26
- **Package:** `com.flipperx.mobile`
- **Tools Tracked:** 31 ToolIds (30 base + 1 new `NETWORK_DIAGNOSTICS`)
- **Build:** Gradle 8.10.2, AGP 8.7.2, Kotlin 2.0.21
- **CI/CD:** Automated GitHub Actions workflow (`.github/workflows/build.yml`)

## What It Does

FlipperX provides a modular, data‑driven platform for security engineers to run sophisticated reconnaissance and assessment tools on mobile devices without requiring root or privileged system access. 

### Core Strengths

1. **Tool‑First Architecture:** 31 defined tools each backed by native Android APIs (`PackageManager`, `WifiManager`, `BluetoothManager`, etc.) and executed via deterministic dispatch from `ToolRegistry` through `RealToolHandlers` to runtime UIs.
2. **Defensive‑First Security:** All tool handlers follow the security model of **read‑only** API consumption, never modifying system state or persisting secrets. No keystore, tokens, or credentials are ever written to disk.
3. **Production‑Ready Build Pipeline:** GitHub Actions (Java 21, SDK 36) with signed releases gated by secrets (SIGNING_KEY, KEY_ALIAS, KEY_PASSWORD, STORE_PASSWORD). The pipeline runs unit tests, builds `debug` and `release` APKs, and runs `apksigner` verification.
4. **Cross‑Platform UI:** Implemented in **Compose** with a dark‑theme, modular screens for Red Team, Blue Team, and White Hat tooling. UI commands map directly to `ViewModel` → `Repository` → `Handler` execution chain.
5. **Zero‑Trust CI/CD:** Pipeline enforces that signing is optional — a debug build is produced regardless of secret presence. Test results are preserved as artifacts for traceability.
6. **Minimal Local Dependencies:** Source code can be developed on any Termux/Android environment, but APK builds are intended for GitHub Actions where the full Android SDK is available. All source artifacts are fully self‑contained.

## Notable Tool Implementations

- **FILE_META:** Native file metadata extraction using `File` and `Stat` APIs.
- **APK_PERMS:** Manifest inspection and permission analysis via `PackageManager`.
- **VULN_ASSESS:** 10‑point device security posture audit (root detection, SELinux, debug flags).
- **BLE_SCANNER:** Bluetooth LE device discovery with runtime permission handling (STATUS: `NEEDS_PERMISSION`).
- **NETWORK_DIAGNOSTICS:** New tool added via `ToolRegistry.kt` (24‑hour snapshot).

## CI/CD Process (GitHub Actions)

**Steps executed automatically on push:**
1. Checkout repository
2. Set up Java 21 (Temurin)
3. Set up Android SDK 36 with NDK
4. Cache Gradle packages
5. **Run unit tests** (`./gradlew test`)
6. Upload test reports
7. Build debug APK
8. Upload debug APK
9. Build unsigned release APK
10. **Verify unsigned APK** (size, sha‑256)
11. **Sign release APK** (optional, gated by secrets)
12. **Verify signed APK** (apksigner, badging)
13. Upload artifacts (unsigned, signed, test results, SHA‑256)
14. Generate build summary

## Deployment

**Local development:**
```bash
# Generate Gradle wrapper (if missing)
./gradlew wrapper --gradle-version 8.10.2

# Inspect source and unit tests locally
./gradlew test --no-daemon   # Will fail locally without Android SDK
```

**Production build (recommended):**
```bash
# Push to GitHub, trigger CI/CD workflow automatically
```

## Security & Compliance

- **No secrets in repo:** `.gitignore` blocks keystore, password, token files.
- **Signing optional:** Release signing is gated by 4 GitHub secrets; if missing, build still proceeds and produces unsigned APK.
- **Defensive tooling:** All tools use read‑only APIs and do not alter system state.
- **Audit trails:** Every tool execution is logged and queryable via `ToolExecutionRepository`.
- **Unit tests:** 15 tests covering registry integrity, handler logic, repository, and ViewModel state.

## Running FlipperX

1. Build APK via CI/CD (recommended) or manually on a device with Android SDK.
2. Import APK to a test device (Redmi Note 14 5G recommended, Android 16, SDK 36).
3. Launch FlipperX and navigate using the bottom navigation bar:
   - **Dashboard:** System info & device stats.
   - **Red Team:** DNS lookup, Port scan, IP info, Network discovery, and more.
   - **Blue Team:** Rogue AP detector, Network monitor, Wi‑Fi inspector.
   - **White Hat:** Base64 utility, Hash generator, JWT inspector, SSL/TLS checker, File analyzer, APK permission inspector, Local vulnerability checklist, Security report generator.
   - **Wireless:** BLE scanner, Wi‑Fi channel analyzer, IR diagnostics, NFC inspector.
4. Each tool UI is a simple card with a single “Run” button that triggers the corresponding ViewModel method and displays output.

## Troubleshooting

**Local `./gradlew test` failures (Termux environment):**
- Cause: Missing Android SDK; AGP requires `android.jar` for mockable Android JARs.
- Fix: Build and test via GitHub Actions CI/CD (recommended). Local development can focus on code changes and unit test authoring; verification must be performed on CI.

**GitHub Actions errors:**
- Check Gradle wrapper version, secrets configuration, and SDK installation logs.
- Refer to step‑by‑step console output for the specific error.

## Support & Further Reading

- Maintainer: oekdosq (GitHub)
- Issues & PRs: https://github.com/oekdosq/flipperx/issues
- CI/CD reports: Available as artifacts after each workflow run

---

*This README was generated as part of the production‑ready source commit (09856e278aeec951088cb577d04924058deac16f) to provide clear documentation for developers and security operators.*
