package com.asiantech.intern20summer1

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.`at-huybui`.activity_main.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    companion object {
        private const val REGEX_EMAIL = """^[a-zA-Z][a-zA-Z0-9_.]*[@][a-zA-Z0-9]+[.][a-zA-Z0-9]+$"""
        private const val REGEX_PASSWORD = """^[A-Z][a-zA-Z0-9]{5,}$""" // regex for password
        private const val SDK_VERSION = 23
    }

    private var bufferEmail = ""          // buffer variable for Email
    private var bufferPass = ""           // buffer variable for Password
    private var bufferRePass = false     // state variable for Rewrite password

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // call handle function for api
        handleStatusBarFollowSdk()
        // call handle function for views
        handleForEditTextEmail()
        handleForEditTextPass()
        handleForEditTextRePass()
        handleForListener()
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
    private fun handleForListener() {
        container?.setOnTouchListener { view, _ ->
            view.requestFocus()
            view.hideKeyboard()
            true
        }

        btnSignUp.setOnClickListener {
            toast(resources.getString(R.string.sign_up_button_click))
        }

        llTextRegister?.setOnClickListener {
            toast(resources.getString(R.string.sign_up_text_view_sign_up))
        }

        imgFacebook?.setOnClickListener {
            toast(resources.getString(R.string.sign_up_text_goto_facebook))
        }

        imgTwitter?.setOnClickListener {
            toast(resources.getString(R.string.sign_up_text_goto_twitter))
        }

        imgGoogle?.setOnClickListener {
            toast(resources.getString(R.string.sign_up_text_goto_google))
        }
    }

    /**
     * Handle function for email edit text
     * This function will check text in box of  email edit text
     * This will control image views to hide and show them
     * This function will change color of box with state of text in box
     */
    private fun handleForEditTextEmail() {
        edtEmail.addTextChangedListener {
            val textEmail = edtEmail.text.toString()
            if (textEmail.isNotEmpty()) {
                bufferEmail = if (textEmail.matches(REGEX_EMAIL.toRegex())) {
                    setIconTickOrErrorForEditText(edtEmail, R.drawable.icon_tick)
                    edtEmail.setBackgroundResource(R.drawable.bg_custom_edit_text_tick)
                    textEmail // set bufferEmail is textEmail variable
                } else {
                    setIconTickOrErrorForEditText(edtEmail, R.drawable.icon_error)
                    edtEmail.setBackgroundResource(R.drawable.bg_custom_edit_text_error)
                    "" // set bufferEmail is empty
                }
            } else {
                setIconTickOrErrorForEditText(edtEmail, 0)
                edtEmail.setBackgroundResource(R.drawable.bg_custom_select_edit_text)
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
    private fun handleForEditTextPass() {
        edtPass.addTextChangedListener { text ->
            val textPass = text.toString()
            if (textPass.isNotEmpty()) {
                bufferPass = if (textPass.matches(REGEX_PASSWORD.toRegex())) {
                    setIconTickOrErrorForEditText(edtPass, R.drawable.icon_tick)
                    edtPass.setBackgroundResource(R.drawable.bg_custom_edit_text_tick)
                    textPass // set bufferPass is textPass variable
                } else {
                    setIconTickOrErrorForEditText(edtPass, R.drawable.icon_error)
                    edtPass.setBackgroundResource(R.drawable.bg_custom_edit_text_error)
                    "" // set bufferPass is empty
                }
            } else {
                setIconTickOrErrorForEditText(edtPass, 0)
                edtPass.setBackgroundResource(R.drawable.bg_custom_select_edit_text)
            }
            // handle for rewrite pass
            val textRePass = edtRePass.text.toString()
            if (textRePass.isNotEmpty()) {
                bufferRePass = if (textRePass == bufferPass) {
                    setIconTickOrErrorForEditText(edtRePass, R.drawable.icon_tick)
                    edtRePass.setBackgroundResource(R.drawable.bg_custom_edit_text_tick)
                    true
                } else {
                    setIconTickOrErrorForEditText(edtRePass, R.drawable.icon_error)
                    edtRePass.setBackgroundResource(R.drawable.bg_custom_edit_text_error)
                    false
                }
            } else {
                setIconTickOrErrorForEditText(edtRePass, 0)
                edtRePass.setBackgroundResource(R.drawable.bg_custom_select_edit_text)
            }
        }
    }

    /**
     * This function will do :
     *          - Check rewrite pass edit text view is empty or is not
     *          - Compare password strings and rewrite password strings is match or not match
     *          - Change color of box
     */
    private fun handleForEditTextRePass() {
        edtRePass.addTextChangedListener { text ->
            val textRePass = text.toString()
            if (textRePass.isNotEmpty()) {
                bufferRePass = if (textRePass == bufferPass) {
                    setIconTickOrErrorForEditText(edtRePass, R.drawable.icon_tick)
                    edtRePass.setBackgroundResource(R.drawable.bg_custom_edit_text_tick)
                    true
                } else {
                    setIconTickOrErrorForEditText(edtRePass, R.drawable.icon_error)
                    edtRePass.setBackgroundResource(R.drawable.bg_custom_edit_text_error)
                    false
                }
            } else {
                setIconTickOrErrorForEditText(edtRePass, 0)
                edtRePass.setBackgroundResource(R.drawable.bg_custom_select_edit_text)
            }
        }
    }

    /**
     * This function will handle for icon of edit text views
     */
    private fun setIconTickOrErrorForEditText(editText: EditText, icon: Int) {
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0)
    }

    /**
     * This function will change color of status bar follow api of device
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun handleStatusBarFollowSdk(){
        if (Build.VERSION.SDK_INT >= SDK_VERSION) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else{
            window.statusBarColor = resources.getColor(R.color.status_bar)
        }
    }

    /**
     * This function will handle toast, it will used to show string to activity display
     */
    private var toastState: Toast? = null
    private fun toast(text: Any, duration: Int = Toast.LENGTH_SHORT) {
        toastState?.cancel()
        toastState = Toast.makeText(this@MainActivity, text.toString(), duration).apply { show() }
    }
}
