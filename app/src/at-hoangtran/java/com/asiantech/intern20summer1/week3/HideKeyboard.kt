@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.asiantech.intern20summer1.week3

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

fun Activity.hideSoftKeyboard() {
    currentFocus?.let {
        val inputMethodManager = ContextCompat.getSystemService(this, InputMethodManager::class.java)
        inputMethodManager?.hideSoftInputFromWindow(it.windowToken, 0)
    }
}
