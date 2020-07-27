package com.asiantech.intern20summer1.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/*
 * Create extension funtion
 */
internal fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

internal inline fun EditText.textChangedListener(
    crossinline beforeTextChanged: (text: CharSequence?, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ -> },
    crossinline onTextChanged: (text: CharSequence?, start: Int, before: Int, count: Int) -> Unit = { _, _, _, _ -> },
    crossinline afterTextChanged: (text: Editable?) -> Unit = {}
): TextWatcher {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s)
        }

        override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged.invoke(text, start, count, after)
        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged.invoke(text, start, before, count)
        }
    }
    addTextChangedListener(textWatcher)
    return textWatcher
}
