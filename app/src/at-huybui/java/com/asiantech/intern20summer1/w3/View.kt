package com.asiantech.intern20summer1.w3

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Function used to hide Keyboard
 */
fun View.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}
