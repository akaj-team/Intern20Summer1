package com.asiantech.intern20summer1.week9

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

internal fun getBitmapFromView(view: View): Bitmap? {
    val bitmap =
        Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

internal fun getBitmapFromView(view: View, defaultColor: Int): Bitmap? {
    val bitmap =
        Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawColor(defaultColor)
    view.draw(canvas)
    return bitmap
}
