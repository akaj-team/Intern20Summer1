package com.asiantech.intern20summer1

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

internal fun View.hideSoftKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
}
