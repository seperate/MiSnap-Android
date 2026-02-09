# GitHub Actions Workflow Changes

## Summary

Fixed critical issues in the Android Build & Release workflow to ensure reliable builds on GitHub Actions.

## Issues Fixed

### 1. Secret Validation Issue
**Problem**: The workflow used `if: ${{ secrets.ANDROID_KEYSTORE_BASE64 != '' }}` which doesn't work in GitHub Actions (secrets can't be checked for emptiness this way).

**Solution**:
- Removed the unreliable conditional check
- Added proper validation using environment variables within the step
- Added `continue-on-error: true` to gracefully handle missing keystore
- Workflow now outputs whether keystore was decoded or not

### 2. Missing Mandatory Secrets Validation
**Problem**: No validation for required GitHub Packages credentials, causing confusing build failures.

**Solution**: Added a "Validate required secrets" step that fails fast with a clear error message if `GH_PACKAGES_USER` or `GH_PACKAGES_TOKEN` are missing.

### 3. No Fallback for Unsigned Builds
**Problem**: Workflow would fail if keystore secrets weren't configured, even for testing purposes.

**Solution**:
- Workflow now builds signed release APK if keystore is available
- Falls back to unsigned debug APK if keystore is not available
- Clearly indicates build type in artifacts and release notes

### 4. Hardcoded Build Paths
**Problem**: APK rename and upload steps assumed release builds, failing for debug builds.

**Solution**:
- Made paths dynamic based on build type (release vs debug)
- Added proper step outputs to pass APK paths between steps
- Added `-unsigned` suffix to debug build filenames

### 5. Unclear Build Status
**Problem**: Users couldn't easily tell if the build was signed or unsigned.

**Solution**:
- Added build type indicator in GitHub Release notes
- Enhanced build summary with build type and APK path
- Added warning for unsigned builds

## Changes Made

### New Steps

1. **Validate required secrets** (line 43-52)
   - Validates `GH_PACKAGES_USER` and `GH_PACKAGES_TOKEN`
   - Fails fast with clear error message

### Modified Steps

2. **Decode Keystore** (line 54-67)
   - Added `continue-on-error: true`
   - Added `id: decode_keystore` for step outputs
   - Checks if secret exists using environment variable
   - Sets `decoded=true/false` output
   - Provides clear feedback

3. **Build Release APK** (line 69-83)
   - Now conditional based on keystore availability
   - Builds `assembleRelease` if keystore exists
   - Builds `assembleDebug` if keystore doesn't exist

4. **Rename APK** (line 96-118)
   - Added `id: rename` for step outputs
   - Determines build type dynamically
   - Searches in correct directory (release or debug)
   - Adds `-unsigned` suffix for debug builds
   - Outputs `apk_path` for later steps

5. **Upload APK Artifact** (line 120-126)
   - Dynamic artifact name: `release-apk` or `debug-apk`
   - Searches all APK directories

6. **Create GitHub Release** (line 128-153)
   - Added build type indicator in release notes
   - Uses dynamic APK path from rename step

7. **Build Summary** (line 155-165)
   - Shows build type (Signed Release vs Unsigned Debug)
   - Shows APK path
   - Warns if unsigned build

## Testing Recommendations

### Test Case 1: Full Signed Build (Production)
**Setup**: Configure all secrets (GH_PACKAGES_*, ANDROID_KEYSTORE_*, etc.)

**Expected Result**:
- ✓ Signed release APK created
- ✓ APK filename: `MitekAndroidDemoApp-{version}.apk`
- ✓ Release notes show "✓ Signed Release Build"

**Test Command**:
```bash
# Push a test tag
git tag v0.0.1-test
git push origin v0.0.1-test
```

### Test Case 2: Unsigned Debug Build (Testing)
**Setup**: Configure only `GH_PACKAGES_USER` and `GH_PACKAGES_TOKEN` (no keystore)

**Expected Result**:
- ✓ Unsigned debug APK created
- ✓ APK filename: `MitekAndroidDemoApp-{version}-unsigned.apk`
- ✓ Release notes show "⚠️ Unsigned Debug Build - Keystore not configured"
- ✓ Build summary warns about unsigned build

**Test Command**:
```bash
# Use manual workflow dispatch from GitHub Actions tab
# Or push a test tag: git tag v0.0.2-test && git push origin v0.0.2-test
```

### Test Case 3: Missing Required Secrets
**Setup**: Don't configure `GH_PACKAGES_USER` or `GH_PACKAGES_TOKEN`

**Expected Result**:
- ✗ Build fails at "Validate required secrets" step
- ✗ Clear error message displayed
- ✗ No wasted time on Gradle builds

## Rollback Plan

If issues arise, revert to commit `ddab56cd77`:
```bash
git checkout ddab56cd77 -- .github/workflows/android-build.yml
```

## Future Improvements

1. **Add NDK installation**: If SDK modules require NDK for building
2. **Add test job**: Run unit tests before building APK
3. **Add lint check**: Run Android lint before building
4. **Matrix builds**: Build for multiple architectures separately
5. **Dependency caching**: Optimize for faster builds (already partially done with setup-java)

## Related Files

- `.github/workflows/android-build.yml` - Main workflow file
- `.github/workflows/README.md` - Workflow documentation
- `CLAUDE.md` - Updated with CI/CD information
