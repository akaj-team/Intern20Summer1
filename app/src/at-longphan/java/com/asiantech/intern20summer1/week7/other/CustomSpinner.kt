package com.asiantech.intern20summer1.week7.other

import android.content.Context
import android.util.AttributeSet
import android.widget.Spinner

class CustomSpinner(context: Context?, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatSpinner(context, attrs) {
    private var listener: OnSpinnerEventsListener? = null
    private var openInitiated = false

    interface OnSpinnerEventsListener {
        /**
         * Callback triggered when the spinner was opened.
         */
        fun onSpinnerOpened(spinner: Spinner?)

        /**
         * Callback triggered when the spinner was closed.
         */
        fun onSpinnerClosed(spinner: Spinner?)
    }

    override fun performClick(): Boolean {
        openInitiated = true
        listener?.onSpinnerOpened(this)
        return super.performClick()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasBeenOpened() && hasFocus) {
            performClosedEvent()
        }
    }

    /**
     * Register the listener which will listen for events.
     */
    fun setSpinnerEventsListener(
        onSpinnerEventsListener: OnSpinnerEventsListener?
    ) {
        listener = onSpinnerEventsListener
    }

    /**
     * Propagate the closed Spinner event to the listener from outside if needed.
     */
    private fun performClosedEvent() {
        openInitiated = false
        listener?.onSpinnerClosed(this)
    }

    /**
     * A boolean flag indicating that the Spinner triggered an open event.
     * @return true for opened Spinner
     */
    private fun hasBeenOpened(): Boolean {
        return openInitiated
    }
}