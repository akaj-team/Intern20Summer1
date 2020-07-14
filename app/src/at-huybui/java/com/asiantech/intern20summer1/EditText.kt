package com.asiantech.intern20summer1

import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener

    private val regexEmail = """^[a-zA-Z][a-zA-Z0-9_.]*[@][a-zA-Z0-9]+[.][a-zA-Z0-9]+$""".toRegex()
    private val regexPass = """^[A-Z][a-zA-Z0-9]{5,}$""".toRegex() // regex for password
    private var bufferEmail = ""          // buffer variable for Email
    private var bufferPass = ""           // buffer variable for Password
    private var bufferRepass = false     // state variable for Rewrite password

    /**
     * Handle function for email edit text
     * This function will check text in box of  email edit text
     * This will control image views to hide and show them
     * This function will change color of box with state of text in box
     */
    fun handleForEdittextEmail(edtEmail: EditText, imgTickEmail: ImageView) {
        edtEmail.addTextChangedListener {
            Log.d("AAA", "change text Email")
            val textEmail = edtEmail.text.toString()
            if (textEmail.isNotEmpty()) {
                imgTickEmail.visibility = View.VISIBLE
                bufferEmail = if (textEmail.matches(regexEmail)) {
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

    fun handleForEdittextPass(
        edtPass: EditText, imgTickPass: ImageView,
        edtRePass: EditText, imgTickRepass: ImageView
    ) {
        edtPass.addTextChangedListener {
            Log.d("AAA", "change text Password")
            val textPass = edtPass.text.toString()
            if (textPass.isNotEmpty()) {
                imgTickPass.visibility = View.VISIBLE
                bufferPass = if (textPass.matches(regexPass)) {
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
                bufferRepass = if (textRepass == bufferPass) {
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
    fun handleForEdittextRePass(edtRePass: EditText, imgTickRepass: ImageView) {
        edtRePass.addTextChangedListener {
            val textRepass = edtRePass.text.toString()
            if (textRepass.isNotEmpty()) {
                imgTickRepass.visibility = View.VISIBLE
                bufferRepass = if (textRepass == bufferPass) {
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
