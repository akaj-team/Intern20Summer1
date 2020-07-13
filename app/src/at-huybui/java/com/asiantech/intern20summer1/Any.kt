package com.asiantech.intern20summer1

import android.content.Context
import android.widget.Toast

/**
 * This function will handle toast, it will used to show string to activity display
 */
private var toastStatus: Toast? = null
fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    toastStatus?.cancel()
    toastStatus = Toast.makeText(context, this.toString(), duration).apply { show() }

}
