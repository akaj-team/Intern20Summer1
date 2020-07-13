package com.asiantech.intern20summer1

import android.content.Context
import android.widget.Toast

    private var mToast: Toast? = null

    fun Any.toast(context: Context ,leng: Int = Toast.LENGTH_SHORT) {
    mToast = Toast.makeText(context, this.toString(), leng).apply { show() }
}
