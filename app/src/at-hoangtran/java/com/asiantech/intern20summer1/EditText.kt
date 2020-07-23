package com.asiantech.intern20summer1

import android.widget.EditText

fun EditText.onFocusEditText() {
    this.setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            this.setBackgroundResource(R.drawable.bg_edit_text_focus)
        } else {
            this.setBackgroundResource(R.drawable.bg_edit_text)
        }
    }
}
