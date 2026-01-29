package com.miteksystems.misnap.examples.license

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.miteksystems.misnap.apputil.LicenseFetcher
import com.miteksystems.misnap.core.LicenseStatus
import com.miteksystems.misnap.core.LicenseUtil

/**
 * This example demonstrates how to use [LicenseUtil.checkLicenseStatus] to get the status of a license.
 */
class LicenseCheck : Fragment() {

    /**
     * Fetch the Misnap SDK license.
     * Good practice: Handle the license in a way that it is remotely updatable.
     */
    private val license by lazy {
        LicenseFetcher.fetch()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Below are 2 examples of how to use the [LicenseUtil.checkLicenseStatus] to check the license status.
         * The first one is a standard license check.
         * The second one includes the optional feature check parameter, in this case "nfc".
         * The examples are independent of each other and should be used separately.
         */

        // License check
        when (LicenseUtil.checkLicenseStatus(license)) {
            LicenseStatus.NOT_VALID -> {
                // Handle license failed validation.
            }
            LicenseStatus.PLATFORM_NOT_SUPPORTED -> {
                //  Handle Android platform not supported.
            }
            LicenseStatus.NOT_VALID_APP_ID -> {
                // Handle current application ID is not supported.
            }
            LicenseStatus.FEATURE_NOT_SUPPORTED -> {
                // As we didn't provide a feature to check (which is optional), this status can be ignored.
            }
            LicenseStatus.EXPIRED -> {
                // Expired license but all functionality is still accessible. Please contact Mitek as soon as possible to avoid loss of functionality.
                // Process with normal flow.
            }
            LicenseStatus.DISABLED -> {
                // Handle disabled license. All functionality is not accessible.
            }
            LicenseStatus.VALID -> {
                // License is valid, proceed with normal flow.
            }
        }

        // License check including feature check, in this example "nfc".
        when (LicenseUtil.checkLicenseStatus(license, "nfc")) {
            LicenseStatus.NOT_VALID -> {
                // Handle license failed validation.
            }
            LicenseStatus.PLATFORM_NOT_SUPPORTED -> {
                //  Handle Android platform not supported.
            }
            LicenseStatus.NOT_VALID_APP_ID -> {
                // Handle current application ID is not supported.
            }
            LicenseStatus.FEATURE_NOT_SUPPORTED -> {
                // Handle feature not supported.
            }
            LicenseStatus.EXPIRED -> {
                // Expired license but all functionality is still accessible. Please contact Mitek as soon as possible to avoid loss of functionality.
                // Process with normal flow.
            }
            LicenseStatus.DISABLED -> {
                // Handle disabled license. All functionality is not accessible.
            }
            LicenseStatus.VALID -> {
                // License is valid, proceed with normal flow.
            }
        }
    }
}
