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
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.`at-hoangtran`.activity_main.*
import kotlinx.android.synthetic.`at-hoangtran`.activity_main.view.*

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ClickableViewAccessibility", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtEmail.onFocusEditText(rlEmail)
        edtPass.onFocusEditText(rlPass)
        edtRetype.onFocusEditText(rlRetype)


        lnMain?.setOnTouchListener { it, _ ->
            it.clearFocus()
            it.requestFocus()
            this.hideSoftKeyboard()
            true
        }

        edtEmail.onTextChange(edtEmail, imgEmailTick, rlEmail)
        edtPass.onTextChange(edtPass, imgPassTick, rlPass)
        edtRetype.onTextChange(edtRetype, imgRetypeTick, rlRetype)

        toastClick()
        if(Build.VERSION.SDK_INT>=23){
            window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.statusBarColor = R.color.White
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

    fun validPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        return password.matches(passwordPattern.toRegex())
    }

    private fun EditText.onTextChange(edt: EditText, tick: ImageView, rl: RelativeLayout) {
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
                        if (str == edtPass.text.toString() && validPassword(str)) {
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
}
