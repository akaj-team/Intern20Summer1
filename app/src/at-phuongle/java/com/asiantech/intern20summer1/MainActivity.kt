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
        setContentView(R.layout.activity_main)

        val fieldEmail = findViewById<EditText>(R.id.edtEmail)
        val imgEmail = findViewById<ImageView>(R.id.imgEmail)
        val fieldPass = findViewById<EditText>(R.id.edtPass)
        val imgPass = findViewById<ImageView>(R.id.imgPass)
        val fieldPass2 = findViewById<EditText>(R.id.edtPass2)
        val imgPass2 = findViewById<ImageView>(R.id.imgPass2)

        fieldEmail.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus && fieldEmail.text.toString().isEmpty()) {
                fieldEmail.setBackgroundResource(R.drawable.edit_text_border_active)
                imgEmail.setBackgroundResource(0)
            } else if (!hasFocus && fieldEmail.text.toString().isEmpty()) {
                fieldEmail.setBackgroundResource(R.drawable.edit_text_border_normal)
                imgEmail.setBackgroundResource(0)
            } else {
                if (isEmailValid(fieldEmail.text.toString())) {
                    fieldEmail.setBackgroundResource(R.drawable.edit_text_border)
                    imgEmail.setBackgroundResource(R.drawable.icon_tick)
                    imgEmail.layoutParams.width = ICON_WIDTH
                    imgEmail.requestLayout()
                } else {
                    fieldEmail.setBackgroundResource(R.drawable.edit_text_border_wrong)
                    imgEmail.setBackgroundResource(R.drawable.icon_error)
                    imgEmail.layoutParams.width = ICON_WIDTH
                    imgEmail.requestLayout()
                }
            }
        }

        fieldPass.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus && fieldPass.text.toString().isEmpty()) {
                fieldPass.setBackgroundResource(R.drawable.edit_text_border_active)
                imgPass.setBackgroundResource(0)
            } else if (!hasFocus && fieldPass.text.toString().isEmpty()) {
                fieldPass.setBackgroundResource(R.drawable.edit_text_border_normal)
                imgPass.setBackgroundResource(0)
            } else {
                if (isPasswordValid(fieldPass.text.toString())) {
                    fieldPass.setBackgroundResource(R.drawable.edit_text_border)
                    imgPass.setBackgroundResource(R.drawable.icon_tick)
                    imgPass.layoutParams.width = ICON_WIDTH
                    imgPass.requestLayout()
                } else {
                    fieldPass.setBackgroundResource(R.drawable.edit_text_border_wrong)
                    imgPass.setBackgroundResource(R.drawable.icon_error)
                    imgPass.layoutParams.width = ICON_WIDTH
                    imgPass.requestLayout()
                }
            }
        }

        fieldPass2.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus && fieldPass2.text.toString().isEmpty()) {
                fieldPass2.setBackgroundResource(R.drawable.edit_text_border_active)
                imgPass2.setBackgroundResource(0)
            } else if (!hasFocus && fieldPass2.text.toString().isEmpty()) {
                fieldPass2.setBackgroundResource(R.drawable.edit_text_border_normal)
                imgPass2.setBackgroundResource(0)
            } else {
                if (isPassword2Valid(fieldPass.text.toString(), fieldPass2.text.toString())) {
                    fieldPass2.setBackgroundResource(R.drawable.edit_text_border)
                    imgPass2.setBackgroundResource(R.drawable.icon_tick)
                    imgPass2.layoutParams.width = ICON_WIDTH
                    imgPass2.requestLayout()
                } else {
                    fieldPass2.setBackgroundResource(R.drawable.edit_text_border_wrong)
                    imgPass2.setBackgroundResource(R.drawable.icon_error)
                    imgPass2.layoutParams.width = ICON_WIDTH
                    imgPass2.requestLayout()
                }
            }
        }
    }

    private fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email.toString())

        return matcher.matches()
    }

    private fun isPasswordValid(pass: String): Boolean {
        var check = true

        if (pass.length < PASSWORD_LENGTH) check = false

        if (!pass[0].isUpperCase()) check = false

        return check
    }

    private fun isPassword2Valid(pass: String, pass2: String): Boolean {
        return pass2 == pass
    }

    fun onSignUpBtn(view: View) {
        Toast.makeText(applicationContext, "Sign Up", Toast.LENGTH_SHORT).show()
    }

    fun onSignUp(view: View) {
        Toast.makeText(applicationContext, "Sign up!", Toast.LENGTH_SHORT).show()
    }
}
