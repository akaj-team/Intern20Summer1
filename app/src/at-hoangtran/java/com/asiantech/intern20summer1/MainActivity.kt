package com.asiantech.intern20summer1

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.`at-hoangtran`.activity_main.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtEmail.onFocusEditText(rlEmail)
        edtPass.onFocusEditText(rlPass)
        edtRetype.onFocusEditText(rlRetype)

        lnMain?.setOnTouchListener { it, _ ->
            it.requestFocus()
            it.clearFocus()
            this.hideSoftKeyboard()
            true
        }

        edtEmail.onTextChange(edtEmail, imgEmailTick, rlEmail)
        edtPass.onTextChange(edtPass, imgPassTick, rlPass)
        edtRetype.onTextChange(edtRetype, imgRetypeTick, rlRetype)

        tvSignUp?.setOnClickListener {
            "Sign up".toast(this@MainActivity)
        }
        imgFB?.setOnClickListener {
            "Facebook".toast(this@MainActivity)
        }
        imgTwitter?.setOnClickListener {
            "Twitter".toast(this@MainActivity)
        }
        imgGGPlus?.setOnClickListener {
            "Google+".toast(this@MainActivity)
        }
        btnSignUp?.setOnClickListener {
            "Sign up".toast(this@MainActivity)
        }
    }

    private fun EditText.onFocusEditText(rl: RelativeLayout) {
        this.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                rl.setBackgroundResource(R.drawable.bg_edit_text_focus)
            } else {
                rl.setBackgroundResource(R.drawable.bg_edit_text)
            }
        }
    }
}
