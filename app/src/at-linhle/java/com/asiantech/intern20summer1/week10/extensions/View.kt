package com.asiantech.intern20summer1.week10.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.asiantech.intern20summer1.week4.extensions.hideSoftKeyboard


internal fun View.hideSoftKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
}

@SuppressLint("ClickableViewAccessibility")
internal fun handleOnTouchScreen(view: View?) {
    view?.setOnTouchListener { it, _ ->
        it.clearFocus()
        it.hideSoftKeyboard()
        true
    }
}
