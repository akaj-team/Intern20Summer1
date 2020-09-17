@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.asiantech.intern20summer1.week12

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideSoftKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
}
