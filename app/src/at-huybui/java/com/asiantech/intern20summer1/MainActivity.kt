package com.asiantech.intern20summer1

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.`at-huybui`.activity_main.*

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
     * handle function of buttons and image views
     * in function have:
     *     - handle for signup button
     *     - handled for image views ( facebook, google, twitter)
     *     - handled for text views (register, sign up!)
     * function do not have parameters and return
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
            if (textEmail.isEmpty() || textPass.isEmpty() || textRepass.isEmpty()) {
                val text =
                    resources.getString(R.string.text_enter_full_email_and_password)
                text.toast()
            } else if (bufferEmail.isEmpty() || bufferPass.isEmpty() || !bufferRepass) {
                val text = resources.getString(R.string.text_email_or_password_is_invalid)
                text.toast()
            } else {
                "Email    : $bufferEmail\nPassword: $bufferPass".toast()
            }
        }

        tvRegister?.setOnClickListener {
            resources.getString(R.string.text_goto_register).toast()
        }
        tvSigup?.setOnClickListener {
            resources.getString(R.string.text_goto_register).toast()
        }
        imgFacebook?.setOnClickListener {
            resources.getString(R.string.text_goto_facebook).toast()
        }
        imgTwitter?.setOnClickListener {
            resources.getString(R.string.text_goto_twitter).toast()
        }
        imgGoogle?.setOnClickListener {
            resources.getString(R.string.text_goto_google).toast()
        }


    }

    /**
     * handle function for email edit text
     * this function will check text in box of  email edit text
     * this will control image views to hide and show them
     * this function will change color of box with state of text in box
     */
    private fun handleForEdittextEmail() {

        edtEmail.addTextChangedListener {
            printLog("change text Email")
            val textEmail = edtEmail.text.toString()
            if (textEmail.isNotEmpty()) {
                imgTickEmail.visibility = View.VISIBLE
                this.bufferEmail =
                    if (textEmail.matches(regexEmail)) {
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
     * this function same handle function for email
     * they are just unlike at variables and views
     *              - this function will check text in box of password edit text view
     *              - it will change color of box
     *              - it will control
     *              - it will set invalid password for bufferPass variable
     *              - final, it wil do work of handle function of rewrite password edit text view
     */

    private fun handleForEdittextPass() {

        edtPass.setOnClickListener {
            resources.getString(R.string.text_rule_password).toast()
        }
        edtPass.setOnFocusChangeListener { _, b ->
            if (b) {
                resources.getString(R.string.text_rule_password).toast()
            }
        }

        edtPass.addTextChangedListener {
            printLog("change text Password")
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
     * this function will do :
     *          - check rewrite pass edit text view is empty or is not
     *          - compare password strings and rewrite password strings is match or not match
     *          - change color of box
     */
    private fun handleForEdittextRePass() {
        edtRePass.addTextChangedListener {
            printLog("change text Rewrite password ")
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
     * this function will used to hide keyboard when click to another views
     */
    private fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    /**
     * this function will handle toast, it will used to show string to activity display
     */
    private var mToast: Toast? = null
    private fun Any.toast(
        context: Context = this@MainActivity,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        mToast?.cancel()
        mToast = Toast.makeText(context, this.toString(), duration).apply { show() }
    }

    /**
     * Log function to debug
     */
    private fun printLog(st: String) {
        Log.d("AAA", st)
    }
}
