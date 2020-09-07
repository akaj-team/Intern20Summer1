package com.asiantech.intern20summer1.w11.views

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 07/09/2020.
 * This is Context TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */

internal fun Context.getScreenWidth(): Int {
    val w = getSystemService(Context.WINDOW_SERVICE) as? WindowManager
    val dimenSion = DisplayMetrics()
    w?.defaultDisplay?.getMetrics(dimenSion)
    return dimenSion.widthPixels
}

internal fun Context.getScreenHeight(): Int {
    val h = getSystemService(Context.WINDOW_SERVICE) as? WindowManager
    val dimenSion = DisplayMetrics()
    h?.defaultDisplay?.getMetrics(dimenSion)
    return dimenSion.heightPixels
}

