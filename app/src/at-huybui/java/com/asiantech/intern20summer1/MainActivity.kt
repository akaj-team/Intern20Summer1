package com.asiantech.intern20summer1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.`at-huybui`.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // hide image views in edit text
        imgTickEmail.visibility = View.INVISIBLE
        imgTickPass.visibility = View.INVISIBLE
        imgTickRepass.visibility = View.INVISIBLE
        // call handle function for views
        handleForEdittextEmail(edtEmail,imgTickEmail)
        handleForEdittextPass(edtPass,imgTickPass,edtRePass,imgTickRepass)
        handleForEdittextRePass(edtRePass,imgTickRepass)
        handleForlistener()
    }

    /**
     * Handle function of buttons and image views
     * In function have:
     *     - Handle for sign up button
     *     - Handled for image views ( facebook, google, twitter)
     *     - Handled for text views (register, sign up!)
     * Function do not have parameters and return
     */
    @SuppressLint("ClickableViewAccessibility")
    private fun handleForlistener() {
        container?.setOnTouchListener { view, _ ->
            view.clearFocus()
            view.requestFocus()
            view.hideKeyboard()
            true
        }

        btnSignup.setOnClickListener {
            resources.getString(R.string.signup_button).toast(this@MainActivity)
        }

        llTextRegister?.setOnClickListener {
            resources.getString(R.string.signup_edt).toast(this@MainActivity)
        }

        imgFacebook?.setOnClickListener {
            resources.getString(R.string.text_goto_facebook).toast(this@MainActivity)
        }

        imgTwitter?.setOnClickListener {
            resources.getString(R.string.text_goto_twitter).toast(this@MainActivity)
        }

        imgGoogle?.setOnClickListener {
            resources.getString(R.string.text_goto_google).toast(this@MainActivity)
        }
    }
}
