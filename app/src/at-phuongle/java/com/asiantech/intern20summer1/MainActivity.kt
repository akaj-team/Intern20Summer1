package com.asiantech.intern20summer1

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Matcher
import java.util.regex.Pattern


private const val ICON_WIDTH = 20
private const val PASSWORD_LENGTH = 6

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.asiantech.intern20summer1.R.layout.activity_main)

        val fieldEmail = findViewById<EditText>(com.asiantech.intern20summer1.R.id.edtEmail)
        val imgEmail = findViewById<ImageView>(com.asiantech.intern20summer1.R.id.imgEmail)
        val fieldPass = findViewById<EditText>(com.asiantech.intern20summer1.R.id.edtPass)
        val imgPass = findViewById<ImageView>(com.asiantech.intern20summer1.R.id.imgPass)
        val fieldPass2 = findViewById<EditText>(com.asiantech.intern20summer1.R.id.edtPass2)
        val imgPass2 = findViewById<ImageView>(com.asiantech.intern20summer1.R.id.imgPass2)

        fieldEmail.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus && fieldEmail.text.toString().isNullOrEmpty()) {
                    fieldEmail.setBackgroundResource(com.asiantech.intern20summer1.R.drawable.edit_text_border_active)
                    imgEmail.setBackgroundResource(0)
                } else if (!hasFocus && fieldEmail.text.toString().isNullOrEmpty()) {
                    fieldEmail.setBackgroundResource(com.asiantech.intern20summer1.R.drawable.edit_text_border_normal)
                    imgEmail.setBackgroundResource(0)
                } else {
                    if (isEmailValid(fieldEmail.text.toString())) {
                        fieldEmail.setBackgroundResource(com.asiantech.intern20summer1.R.drawable.edit_text_border)
                        imgEmail.setBackgroundResource(com.asiantech.intern20summer1.R.drawable.icon_tick)
                        imgEmail.layoutParams.width = ICON_WIDTH
                        imgEmail.requestLayout()
                    } else {
                        fieldEmail.setBackgroundResource(com.asiantech.intern20summer1.R.drawable.edit_text_border_wrong)
                        imgEmail.setBackgroundResource(com.asiantech.intern20summer1.R.drawable.icon_error)
                        imgEmail.layoutParams.width = ICON_WIDTH
                        imgEmail.requestLayout()
                    }
                }
            }
        }

        fieldPass.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus && fieldPass.text.toString().isNullOrEmpty()) {
                    fieldPass.setBackgroundResource(com.asiantech.intern20summer1.R.drawable.edit_text_border_active)
                    imgPass.setBackgroundResource(0)
                } else if (!hasFocus && fieldPass.text.toString().isNullOrEmpty()) {
                    fieldPass.setBackgroundResource(com.asiantech.intern20summer1.R.drawable.edit_text_border_normal)
                    imgPass.setBackgroundResource(0)
                } else {
                    if (isPasswordValid(fieldPass.text.toString())) {
                        fieldPass.setBackgroundResource(com.asiantech.intern20summer1.R.drawable.edit_text_border)
                        imgPass.setBackgroundResource(com.asiantech.intern20summer1.R.drawable.icon_tick)
                        imgPass.layoutParams.width = ICON_WIDTH
                        imgPass.requestLayout()
                    } else {
                        fieldPass.setBackgroundResource(com.asiantech.intern20summer1.R.drawable.edit_text_border_wrong)
                        imgPass.setBackgroundResource(com.asiantech.intern20summer1.R.drawable.icon_error)
                        imgPass.layoutParams.width = ICON_WIDTH
                        imgPass.requestLayout()
                    }
                }
            }
        }

        fieldPass2.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus && fieldPass2.text.toString().isNullOrEmpty()) {
                    fieldPass2.setBackgroundResource(com.asiantech.intern20summer1.R.drawable.edit_text_border_active)
                    imgPass2.setBackgroundResource(0)
                } else if (!hasFocus && fieldPass2.text.toString().isNullOrEmpty()) {
                    fieldPass2.setBackgroundResource(com.asiantech.intern20summer1.R.drawable.edit_text_border_normal)
                    imgPass2.setBackgroundResource(0)
                } else {
                    if (isPassword2Valid(fieldPass.text.toString(), fieldPass2.text.toString())) {
                        fieldPass2.setBackgroundResource(com.asiantech.intern20summer1.R.drawable.edit_text_border)
                        imgPass2.setBackgroundResource(com.asiantech.intern20summer1.R.drawable.icon_tick)
                        imgPass2.layoutParams.width = ICON_WIDTH
                        imgPass2.requestLayout()
                    } else {
                        fieldPass2.setBackgroundResource(com.asiantech.intern20summer1.R.drawable.edit_text_border_wrong)
                        imgPass2.setBackgroundResource(com.asiantech.intern20summer1.R.drawable.icon_error)
                        imgPass2.layoutParams.width = ICON_WIDTH
                        imgPass2.requestLayout()
                    }
                }
            }
        }
    }

    fun onSignUp(v: View?) {
        Toast.makeText(applicationContext, "Sign up!", Toast.LENGTH_SHORT).show()
    }

    fun onSignUpBtn(view: View?) {
        Toast.makeText(applicationContext, "Sign Up", Toast.LENGTH_SHORT).show()
    }

    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)

        return matcher.matches()
    }

    fun isPasswordValid(pass: String): Boolean {
        var check: Boolean = true

        if (pass.length < PASSWORD_LENGTH) {
            check = false
        }

        if (!pass[0].isUpperCase()) {
            check = false
        }

        return check
    }

    fun isPassword2Valid(pass: String, pass2: String): Boolean {
        return pass2 == pass
    }
}
