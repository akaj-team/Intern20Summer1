package com.asiantech.intern20summer1

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import kotlinx.android.synthetic.`at-hoangtran`.activity_main.view.*

var edtP: String = ""

fun validPassword(password: String): Boolean {
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
    return password.matches(passwordPattern.toRegex())
}

fun EditText.onTextChange(edt: EditText, tick: ImageView, rl: RelativeLayout) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val str = edt.text.toString()
            if (str.isEmpty()) {
                tick.visibility = View.INVISIBLE
                rl.setBackgroundResource(R.drawable.bg_edit_text_focus)
            } else when (edt) {
                edtEmail -> {
                    if (Patterns.EMAIL_ADDRESS.matcher(str).matches()) {
                        tick.visibility = View.VISIBLE
                        tick.setImageResource(R.mipmap.icon_tick)
                    } else {
                        tick.visibility = View.VISIBLE
                        tick.setImageResource(R.mipmap.icon_error)
                    }
                }
                edtPass -> {
                    edtP = str
                    if (validPassword(str)) {
                        tick.visibility = View.VISIBLE
                        tick.setImageResource(R.mipmap.icon_tick)
                        rl.setBackgroundResource(R.drawable.bg_edit_text_focus)
                    } else {
                        tick.visibility = View.VISIBLE
                        tick.setImageResource(R.mipmap.icon_error)
                        rl.setBackgroundResource(R.drawable.bg_wrong_password)
                    }
                }
                edtRetype -> {
                    if (str == edtP && validPassword(str)) {
                        tick.visibility = View.VISIBLE
                        tick.setImageResource(R.mipmap.icon_tick)
                        rl.setBackgroundResource(R.drawable.bg_edit_text_focus)
                    } else {
                        tick.visibility = View.VISIBLE
                        tick.setImageResource(R.mipmap.icon_error)
                        rl.setBackgroundResource(R.drawable.bg_wrong_password)
                    }
                }
            }
        }
    })
}
