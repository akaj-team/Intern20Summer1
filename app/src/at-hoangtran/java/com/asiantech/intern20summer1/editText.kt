package com.asiantech.intern20summer1

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import kotlinx.android.synthetic.`at-hoangtran`.activity_main.view.*

fun EditText.onTextChange(edt: EditText, tick: ImageView, rl: RelativeLayout) {
    val main = MainActivity()
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val str = edt.text
            if (str.toString().isEmpty()) {
                tick.visibility = View.INVISIBLE
                rl.setBackgroundResource(R.drawable.on_text_change)
            } else when (edt) {
                edtEmail -> {
                    if (Patterns.EMAIL_ADDRESS.matcher(str.toString()).matches()) {
                        tick.visibility = View.VISIBLE
                        tick.setImageResource(R.mipmap.icon_tick)
                    } else {
                        tick.visibility = View.VISIBLE
                        tick.setImageResource(R.mipmap.icon_error)
                    }
                }
                edtPass -> {
                    if (main.validPassword(str.toString())) {
                        tick.visibility = View.VISIBLE
                        tick.setImageResource(R.mipmap.icon_tick)
                        rl.setBackgroundResource(R.drawable.on_text_change)
                    } else {
                        tick.visibility = View.VISIBLE
                        tick.setImageResource(R.mipmap.icon_error)
                        rl.setBackgroundResource(R.drawable.wrong_password)
                    }
                }
                edtRetype -> {
                    if (str.toString() == edtPass.text.toString() && main.validPassword(str.toString())) {
                        tick.visibility = View.VISIBLE
                        tick.setImageResource(R.mipmap.icon_tick)
                        rl.setBackgroundResource(R.drawable.on_text_change)
                    } else {
                        tick.visibility = View.VISIBLE
                        tick.setImageResource(R.mipmap.icon_error)
                        rl.setBackgroundResource(R.drawable.wrong_password)
                    }
                }
            }
        }
    })
}
