package com.asiantech.intern20summer1

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.`at-huybui`.activity_main.*
import values.toast

class MainActivity : AppCompatActivity() {

    private val regexEmail = """^[a-z][a-z0-9_.]{5,32}[@][a-z0-9]{2,}(.[a-z0-9]{2,4}){1,2}${'$'}"""
        .toRegex() // regex for email
    private val regexPass =
        """(?=^.{8,}${'$'})((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*${'$'}"""
            .toRegex() // regex for password
    private var bufferEmail = ""          // buffer variable for Email
    private var bufferPass = ""           // buffer variable for Password
    private var bufferRepass = false     // state variable for Rewrite password
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // hide image views in edit text
        imgTickEmail.visibility = View.INVISIBLE
        imgTickPass.visibility = View.INVISIBLE
        imgTickRepass.visibility = View.INVISIBLE
        // call handle function for views
        handleForEdittextEmail()
        handleForEdittextPass()
        handleForEdittextRePass()
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
            val textEmail = edtEmail.text.toString()
            val textPass = edtPass.text.toString()
            val textRepass = edtRePass.text.toString()
            when (true) {
                textEmail.isEmpty(), textPass.isEmpty(), textRepass.isEmpty() -> {
                    val text = resources.getString(R.string.text_enter_full_email_and_password)
                    text.toast(this)
                }
                bufferEmail.isEmpty(), bufferPass.isEmpty(), !bufferRepass -> {
                    val text = resources.getString(R.string.text_email_or_password_is_invalid)
                    text.toast(this)
                }
                else -> {
                    "Email    : $bufferEmail\nPassword: $bufferPass".toast(this@MainActivity)
                }
            }
        }

        tvRegister?.setOnClickListener {
            resources.getString(R.string.text_goto_register).toast(this@MainActivity)
        }

        tvSigup?.setOnClickListener {
            resources.getString(R.string.text_goto_register).toast(this@MainActivity)
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

    /**
     * Handle function for email edit text
     * This function will check text in box of  email edit text
     * This will control image views to hide and show them
     * This function will change color of box with state of text in box
     */
    private fun handleForEdittextEmail() {
        edtEmail.addTextChangedListener {
            Log.d("AAA", "change text Email")
            val textEmail = edtEmail.text.toString()
            if (textEmail.isNotEmpty()) {
                imgTickEmail.visibility = View.VISIBLE
                this.bufferEmail = if (textEmail.matches(regexEmail)) {
                    imgTickEmail.setImageResource(R.drawable.icon_tick)
                    edtEmail.setBackgroundResource(R.drawable.custom_edittext_tick)
                    textEmail // set bufferEmail is textEmail variable
                } else {
                    imgTickEmail.setImageResource(R.drawable.icon_error)
                    edtEmail.setBackgroundResource(R.drawable.custom_edittext_error)
                    "" // set bufferEmail is empty
                }
            } else {
                imgTickEmail.visibility = View.INVISIBLE
                edtEmail.setBackgroundResource(R.drawable.custom_select_edittext)
            }
        }
    }

    /**
     * This function same handle function for email
     * They are just unlike at variables and views
     *              - This function will check text in box of password edit text view
     *              - It will change color of box
     *              - It will control
     *              - It will set invalid password for bufferPass variable
     *              - Final, it wil do work of handle function of rewrite password edit text view
     */

    private fun handleForEdittextPass() {
        edtPass.setOnClickListener {
            resources.getString(R.string.text_rule_password).toast(this@MainActivity)
        }

        edtPass.setOnFocusChangeListener { _, b ->
            if (b) {
                resources.getString(R.string.text_rule_password).toast(this@MainActivity)
            }
        }

        edtPass.addTextChangedListener {
            Log.d("AAA", "change text Password")
            val textPass = edtPass.text.toString()
            if (textPass.isNotEmpty()) {
                imgTickPass.visibility = View.VISIBLE
                this.bufferPass = if (textPass.matches(regexPass)) {
                    imgTickPass.setImageResource(R.drawable.icon_tick)
                    edtPass.setBackgroundResource(R.drawable.custom_edittext_tick)
                    textPass // set bufferPass is textPass variable
                } else {
                    imgTickPass.setImageResource(R.drawable.icon_error)
                    edtPass.setBackgroundResource(R.drawable.custom_edittext_error)
                    "" // set bufferPass is empty
                }
            } else {
                imgTickPass.visibility = View.INVISIBLE
                edtPass.setBackgroundResource(R.drawable.custom_select_edittext)
            }
            val textRepass = edtRePass.text.toString()
            if (textRepass.isNotEmpty()) {
                imgTickRepass.visibility = View.VISIBLE
                this.bufferRepass = if (textRepass == bufferPass) {
                    imgTickRepass.setImageResource(R.drawable.icon_tick)
                    edtRePass.setBackgroundResource(R.drawable.custom_edittext_tick)
                    true
                } else {
                    imgTickRepass.setImageResource(R.drawable.icon_error)
                    edtRePass.setBackgroundResource(R.drawable.custom_edittext_error)
                    false
                }
            } else {
                imgTickRepass.visibility = View.INVISIBLE
                edtRePass.setBackgroundResource(R.drawable.custom_select_edittext)
            }
        }
    }

    /**
     * This function will do :
     *          - Check rewrite pass edit text view is empty or is not
     *          - Compare password strings and rewrite password strings is match or not match
     *          - Change color of box
     */
    private fun handleForEdittextRePass() {
        edtRePass.addTextChangedListener {
            val textRepass = edtRePass.text.toString()
            if (textRepass.isNotEmpty()) {
                imgTickRepass.visibility = View.VISIBLE
                this.bufferRepass = if (textRepass == bufferPass) {
                    imgTickRepass.setImageResource(R.drawable.icon_tick)
                    edtRePass.setBackgroundResource(R.drawable.custom_edittext_tick)
                    true
                } else {
                    imgTickRepass.setImageResource(R.drawable.icon_error)
                    edtRePass.setBackgroundResource(R.drawable.custom_edittext_error)
                    false
                }
            } else {
                imgTickRepass.visibility = View.INVISIBLE
                edtRePass.setBackgroundResource(R.drawable.custom_select_edittext)
            }
        }
    }
}
