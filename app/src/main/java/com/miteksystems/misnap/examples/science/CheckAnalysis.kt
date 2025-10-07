package com.miteksystems.misnap.examples.science

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.miteksystems.misnap.apputil.LicenseFetcher
import com.miteksystems.misnap.controller.MiSnapController
import com.miteksystems.misnap.controller.MiSnapController.ErrorResult
import com.miteksystems.misnap.controller.MiSnapController.FrameResult
import com.miteksystems.misnap.core.Frame
import com.miteksystems.misnap.core.MiSnapSettings

/**
 * This example demonstrates a direct integration with MiSnap SDK's document analysis science and checks use case through
 * the [MiSnapController], this type of integration is best suited for developers that want to
 *  interface with the science directly and that will take care of supplying the frames themselves.
 *
 * Notes:
 * - Ensure the provided license enables the required features for your sessions.
 * - Real-Time Security is NOT compatible with controller-based science integrations.
 *
 * See also:
 * - Frame building example: com.miteksystems.misnap.examples.science.FrameFromNativeCamera
 */
class CheckAnalysis : Fragment() {
    private val license by lazy { LicenseFetcher.fetch() }
    private lateinit var misnapController: MiSnapController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        misnapController = initCheckAnalysis()
        /**
         * Call [startAnalysis] once the controller is ready to analyze [Frame]s.
         */
    }

    private fun startAnalysis(frame: Frame) {
        misnapController.analyzeFrame(frame, forceFrameResult = false)
    }

    /**
     * Create a [MiSnapController] capable of analyzing frames for a Document session for check use case, then observe
     * the different [LiveData] objects to get notified about feedback results, final results, errors,
     * etc.
     * Once the controller is ready and initialized create a [Frame] object and use the [MiSnapController.analyzeFrame]
     * method to analyze the frame.
     *
     * @see com.miteksystems.misnap.examples.science.FrameFromNativeCamera for examples on how to
     * build a [Frame] object from different camera APIs.
     */
    private fun initCheckAnalysis(): MiSnapController {
        //Set MiSnap settings for checks (E.g., CHECK_FRONT).
        val settings = MiSnapSettings(MiSnapSettings.UseCase.CHECK_FRONT, license).apply {
            analysis.document.check.geo = MiSnapSettings.Analysis.Document.Check.Geo.US
            analysis.document.trigger = MiSnapSettings.Analysis.Document.Trigger.AUTO
        }

        return MiSnapController.create(requireContext(), settings).apply {
            /**
             * Observe the [MiSnapController.feedbackResult] [LiveData] to handle the feedback from
             * the analyzed frames and handle them accordingly, e.g. by showing the corresponding
             * instructions on screen based on the [MiSnapController.FeedbackResult.userAction],
             * showing the detected document corners using [MiSnapController.FeedbackResult.corners]
             * or the detected glare corners in [MiSnapController.FeedbackResult.glareCorners].
             */
            feedbackResult.observe(viewLifecycleOwner) { feedback ->

            }

            /**
             * Observe the [MiSnapController.frameResult] [LiveData] to handle the successful results
             * of a session, e.g. by collecting the frame data in [FrameResult.DocumentAnalysis.frame]
             * to send it to the server.
             */
            frameResult.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is FrameResult.DocumentAnalysis -> {

                    }
                    else -> {}
                }
            }

            /**
             * Observe the [MiSnapController.errorResult] [LiveData] to handle errors during the
             * analysis of frames.
             *
             * @see [ErrorResult] for all the possible error types emitted.
             */
            errorResult.observe(viewLifecycleOwner) { err ->
                when (err) {
                    is ErrorResult.DocumentAnalysis -> {  }
                    else -> {  }
                }
            }
        }
    }
}
