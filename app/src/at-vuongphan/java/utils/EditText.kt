package utils

import android.view.View
import android.widget.EditText

inline fun EditText.onFocusChange() {
    onFocusChangeListener = View.OnFocusChangeListener { view, b -> }
}
