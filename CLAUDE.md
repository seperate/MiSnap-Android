# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is the **MiSnap SDK v5.10.0 for Android** - Mitek's mobile capture SDK for identity documents, checks, barcodes, biometrics (face/voice), and NFC reading. The repository contains:
- MiSnap SDK modules (distributed via Maven)
- Sample application (`app`) demonstrating SDK integration
- Utility module (`app-util`)
- Comprehensive documentation

## Build System

### Requirements
- **Java**: JDK 17 (for CI/CD), JDK 11 for local development
- **Gradle**: 8.0.2 (via wrapper)
- **Android Gradle Plugin**: 8.1.0
- **Kotlin**: 1.8.10
- **NDK**: r27c (27.1.12297006)
- **Min SDK**: 24
- **Target SDK**: 32
- **Compile SDK**: 34

### Maven Authentication

The SDK artifacts are hosted on GitHub Packages. Authentication is configured via environment variables in `settings.gradle`:
- `GH_PACKAGES_USER`: GitHub username
- `GH_PACKAGES_TOKEN`: Personal Access Token with `read:packages` scope

For local testing, these can be set in the environment or the sample app will use mavenLocal() as a fallback.

### Common Commands

**Note**: Due to sandbox restrictions, you may need to disable the sandbox when running Gradle commands. The Gradle wrapper is located at `./gradlew`.

```bash
# Build the sample app (debug)
./gradlew assembleDebug

# Build the sample app (release - requires keystore configuration)
./gradlew assembleRelease

# Clean build artifacts
./gradlew clean

# Run lint checks
./gradlew lint

# Generate documentation (if applicable)
./gradlew dokka
```

### Release Keystore Configuration

The release build requires these environment variables:
- `KEYSTORE_PASSWORD`: Keystore password
- `KEY_ALIAS`: Key alias
- `KEY_PASSWORD`: Key password
- Keystore file: `app/release-keystore.jks`

## Architecture

### SDK Modules

The MiSnap SDK is **modular**. Integrators include only the modules they need:

- **`document`**: Identity document and check capture sessions
- **`classifier`**: On-device document classification
- **`barcode`**: Barcode scanning sessions
- **`biometric`**: Face + voice biometric sessions
- **`face`**: Face-only sessions
- **`voice`**: Voice-only sessions
- **`combined-nfc`**: Automatic NFC credential extraction + chip reading
- **`nfc`**: NFC chip reading only

Each module has `compileOnly` dependencies to keep size minimal.

### Integration Layers

The SDK provides multiple integration options (in order of recommendation):

1. **Activity-based** (`MiSnapWorkflowActivity`): Easiest, multi-activity architecture
2. **Fragment-based**: Single-activity architecture
3. **Views**: Custom UI/UX using SDK views
4. **Science**: Headless processing, no UI

See `documentation/activity_integration_guide.md` and related guides for details.

### Sample App Structure

```
app/src/main/java/com/miteksystems/misnap/
├── examples/               # Code examples for integration
│   ├── activity/          # Activity-based integration
│   ├── fragment/          # Fragment-based integration
│   ├── views/             # Custom view examples
│   ├── science/           # Science layer examples (headless)
│   ├── camera/            # Camera capability checking
│   ├── license/           # License validation
│   ├── settings/          # SDK configuration
│   └── serverconnection/  # Server API examples (MobileVerify, MiPass)
└── sampleapp/             # Demo UI
    ├── DemoActivity.kt    # Main demo launcher
    ├── DemoFragment.kt    # Session configuration UI
    └── results/           # Results display
```

### Key Concepts

**License Management**: The SDK requires a valid license key for all features. Licenses should be fetched from a server (not hardcoded) and passed via `MiSnapSettings`. See `examples/license/LicenseCheck.kt`.

**MiSnapWorkflowStep**: Represents a single session (document, face, NFC, etc.) with its configuration (`MiSnapSettings`).

**Results**: Retrieved from `MiSnapWorkflowActivity.Result` singleton after session completion. Results are ordered by workflow step sequence.

**Camera Capabilities**: Always query camera support using `CameraSupport.kt` patterns before starting sessions to optimize UX. See `examples/camera/CameraSupport.kt`.

**Device Classification (ODC)**: On-device classification is computationally intensive. Use `DeviceCapabilityUtil.isDeviceSuitableForOdc` to check device suitability before enabling.

## ProGuard/R8

The SDK includes all necessary ProGuard rules. For modules with `compileOnly` dependencies that you're not using, add to `proguard-rules.pro`:

```
-dontwarn com.miteksystems.misnap.**
```

The SDK's native libraries (`libomp.so`) are automatically preserved.

## Testing & Permissions

### Required Permissions

The SDK's modules declare these permissions in their manifests:
- `android.permission.CAMERA`: Required for all image capture sessions
- `android.permission.RECORD_AUDIO`: Voice sessions or video recording with audio
- `android.permission.VIBRATE`: Haptic feedback
- `android.permission.NFC`: NFC reading
- `android.permission.MODIFY_AUDIO_SETTINGS`: Voice sessions

### Sample App License

The sample app includes a valid test license. To run it, just ensure Maven credentials are configured.

## CI/CD

### GitHub Actions Workflows

**`android-build.yml`**: Builds release APK on version tags (e.g., `v1.0.0`) or manual dispatch.
- Requires secrets: `ANDROID_KEYSTORE_BASE64`, `KEYSTORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD`, `GH_PACKAGES_USER`, `GH_PACKAGES_TOKEN`
- Outputs: APK artifact + GitHub Release

## Documentation

All integration guides and documentation are in the `documentation/` directory:

- `activity_integration_guide.md`: **Recommended** integration method
- `fragment_integration_guide.md`: Single-activity apps
- `views_integration_guide.md`: Custom UI/UX
- `science_integration_guide.md`: Headless processing
- `customization_guide.md`: UI/UX customization options
- `migration_guide.md`: Version upgrade instructions
- `code_examples.md`: Code example index
- `nfc_regions_documents_supported.md`: NFC support matrix
- `devices_tested.md`: Tested device list
- `download_sizes.md`: APK size impact

## Important Notes

- **Never hardcode licenses**: Always fetch from server
- **Modular dependencies**: Only include SDK modules you need to minimize APK size
- **ABI splits**: Use App Bundles or APK splits to reduce size (see README FAQ)
- **Native libraries**: SDK includes native code for `armeabi-v7a`, `arm64-v8a`, `x86_64`
- **Version references**: SDK version is defined in `versions.gradle` and `libs.versions.toml`
- **Multi-session workflows**: Multiple `MiSnapWorkflowStep`s can be chained in a single workflow
- **MLKit dependencies**: Face and document classifier modules use Google MLKit (see MLKit known issues)

## Working with This Repository

When making changes to the sample app:
1. Respect the existing example structure in `examples/` - these are reference implementations
2. The sample app (`sampleapp/`) demonstrates all SDK features
3. ProGuard rules in `app/proguard-rules.pro` are configured for the sample app
4. Release builds require proper keystore configuration

## Environment Variables

Ensure these are set for full functionality:
- `GH_PACKAGES_USER`: GitHub username for Maven packages
- `GH_PACKAGES_TOKEN`: GitHub PAT with `read:packages` scope
- `KEYSTORE_PASSWORD`: For release builds
- `KEY_ALIAS`: For release builds
- `KEY_PASSWORD`: For release builds
