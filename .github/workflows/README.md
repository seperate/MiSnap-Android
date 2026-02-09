# GitHub Actions Workflows

This directory contains GitHub Actions workflows for the MiSnap Android SDK repository.

## Workflows

### `android-build.yml` - Android Build & Release

Builds Android APK files and creates GitHub releases.

**Triggers:**
- **Tag push**: Automatically builds and releases when a version tag is pushed (e.g., `v1.0.0`, `v2.1.3`)
- **Manual dispatch**: Can be triggered manually from the Actions tab with a custom version name

**Required Secrets:**
- `GH_PACKAGES_USER`: GitHub username for accessing private MiSnap Maven packages
- `GH_PACKAGES_TOKEN`: GitHub Personal Access Token with `read:packages` scope

**Optional Secrets (for signed releases):**
- `ANDROID_KEYSTORE_BASE64`: Base64-encoded Android keystore file
- `KEYSTORE_PASSWORD`: Keystore password
- `KEY_ALIAS`: Key alias in the keystore
- `KEY_PASSWORD`: Key password

**Workflow Behavior:**

1. **With Keystore Secrets**: Builds a signed release APK
2. **Without Keystore Secrets**: Builds an unsigned debug APK (useful for testing)

The workflow automatically detects which secrets are available and adjusts the build type accordingly.

**Outputs:**
- APK artifact uploaded to GitHub Actions (30-day retention)
- GitHub Release created (only for tag triggers) with the APK attached
- Build summary with APK details

### `claude.yml` - Claude Code Integration

Enables Claude Code to respond to mentions in issues, pull requests, and comments.

**Triggers:**
- Issue comments containing `@claude`
- PR review comments containing `@claude`
- Issues opened/assigned containing `@claude`
- PR reviews containing `@claude`

**Required Secrets:**
- `CLAUDE_CODE_OAUTH_TOKEN`: Anthropic API key for Claude Code

## Setting Up Secrets

### For GitHub Packages Access (Required)

1. Create a GitHub Personal Access Token:
   - Go to GitHub Settings → Developer settings → Personal access tokens → Tokens (classic)
   - Generate new token with `read:packages` scope
   - Copy the token

2. Add secrets to your repository:
   - Go to repository Settings → Secrets and variables → Actions
   - Add `GH_PACKAGES_USER` (your GitHub username)
   - Add `GH_PACKAGES_TOKEN` (the PAT from step 1)

### For Signed Release Builds (Optional)

1. Encode your keystore to base64:
   ```bash
   base64 -i path/to/your-keystore.jks | pbcopy  # macOS
   base64 -w 0 path/to/your-keystore.jks  # Linux
   ```

2. Add keystore secrets to your repository:
   - `ANDROID_KEYSTORE_BASE64`: The base64 string from step 1
   - `KEYSTORE_PASSWORD`: Your keystore password
   - `KEY_ALIAS`: Your key alias
   - `KEY_PASSWORD`: Your key password

## Manual Workflow Dispatch

To manually trigger a build:

1. Go to the **Actions** tab in your repository
2. Select **Android Build & Release** workflow
3. Click **Run workflow**
4. Enter a version name (e.g., `1.0.0-beta`, `test-build`)
5. Click **Run workflow**

The build will create an artifact that you can download from the workflow run page.

## Creating a Release

To create an official release:

1. Ensure all secrets are configured (including keystore for signed builds)
2. Create and push a version tag:
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```

3. The workflow will automatically:
   - Build a signed release APK
   - Create a GitHub Release
   - Attach the APK to the release

## Troubleshooting

### Build fails with "GitHub Packages credentials are required"

**Cause**: `GH_PACKAGES_USER` or `GH_PACKAGES_TOKEN` secrets are not configured.

**Solution**: Follow the "For GitHub Packages Access" setup instructions above.

### Build succeeds but produces unsigned APK

**Cause**: Keystore secrets are not configured.

**Impact**: The APK will be unsigned (debug build). This is acceptable for testing but not for production releases.

**Solution**: If you need signed releases, follow the "For Signed Release Builds" setup instructions above.

### Workflow doesn't trigger on tag push

**Cause**: Tag format doesn't match the pattern `v*.*.*`

**Solution**: Use semantic versioning tags like `v1.0.0`, `v2.1.3`, etc.

### APK build fails during Gradle execution

**Common causes**:
1. **Maven credentials invalid**: Verify `GH_PACKAGES_TOKEN` has `read:packages` scope
2. **Gradle version mismatch**: Workflow uses Gradle wrapper, should be automatic
3. **NDK not found**: Workflow doesn't install NDK - this might be an issue if SDK modules require it

**Solution**: Check the workflow logs for specific error messages and verify all required dependencies are correctly specified in `build.gradle`.

## Workflow Maintenance

When updating the workflows:

1. Always test changes with manual dispatch first
2. Validate YAML syntax before committing
3. Update this README if behavior changes
4. Consider the impact on both signed and unsigned builds
