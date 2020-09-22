package com.asiantech.intern20summer1.w11.utils.extension

import android.content.Context
import android.widget.Toast

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 22/09/2020.
 * This is Any extension
 */

fun Any.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this.toString(), duration).show()
}
