package com.asiantech.intern20summer1

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.`at-hoangtran`.activity_main.*

@Suppress("DEPRECATION", "DEPRECATION", "DEPRECATION", "DEPRECATION")
class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ClickableViewAccessibility", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtEmail.onFocusEditText()
        edtPass.onFocusEditText()
        edtRetype.onFocusEditText()

        lnMain?.setOnTouchListener { it, _ ->
            it.requestFocus()
            this.hideSoftKeyboard()
            true
        }

        onTextChange(edtEmail, imgEmailTick)
        onTextChange(edtPass, imgPassTick)
        onTextChange(edtRetype, imgRetypeTick)

        toastClick()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

     private fun toastClick(){
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

    fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^[A-Z](?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$"
        return password.matches(passwordPattern.toRegex())
    }

    private fun onTextChange(edt: EditText, tick: ImageView) {
        edt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val str = s.toString()
                tick.visibility = View.VISIBLE
                if (str.isEmpty()) {
                    tick.visibility = View.INVISIBLE
                    edt.setBackgroundResource(R.drawable.bg_edit_text_focus)
                } else when (edt) {
                    edtEmail -> {
                        if (Patterns.EMAIL_ADDRESS.matcher(str).matches()) {
                            tick.setImageResource(R.mipmap.icon_tick)
                        } else {
                            tick.setImageResource(R.mipmap.icon_error)
                        }
                    }
                    edtPass -> {
                        if (isValidPassword(str)) {
                            tick.setImageResource(R.mipmap.icon_tick)
                            edt.setBackgroundResource(R.drawable.bg_edit_text_focus)
                        } else {
                            tick.setImageResource(R.mipmap.icon_error)
                            edt.setBackgroundResource(R.drawable.bg_wrong_password)
                        }
                    }
                    edtRetype -> {
                        if (str == edtPass.text.toString() && isValidPassword(str)) {
                            tick.setImageResource(R.mipmap.icon_tick)
                            edt.setBackgroundResource(R.drawable.bg_edit_text_focus)
                        } else {
                            tick.setImageResource(R.mipmap.icon_error)
                            edt.setBackgroundResource(R.drawable.bg_wrong_password)
                        }
                    }
                }
            }
        })
    }
}
