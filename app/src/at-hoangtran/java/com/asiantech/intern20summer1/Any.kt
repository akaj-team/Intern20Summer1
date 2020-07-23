package com.asiantech.intern20summer1

import android.content.Context
import android.widget.Toast

private var toast: Toast? = null

internal fun Any.toast(context: Context, leng: Int = Toast.LENGTH_SHORT) {
    toast = Toast.makeText(context, this.toString(), leng).apply { show() }
}
