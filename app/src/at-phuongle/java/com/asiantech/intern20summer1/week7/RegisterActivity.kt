package com.asiantech.intern20summer1.week7

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.utils.hideSoftKeyboard
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-phuongle`.activity_register.*

class RegisterActivity : AppCompatActivity() {
    companion object {
        const val GALLERY_REQUEST_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    data?.data?.let { uri ->
                        Glide.with(this)
                            .load(uri)
                            .placeholder(R.mipmap.ic_launcher_round)
                            .centerCrop()
                            .into(imgRegisterAvatar)
                    }
                }
            }
        }
    }

    private fun initListener() {
        handleOnTouchScreenListener()
        handleAvatarImageListener()
        handleUserNameEditTextListener()
        handleUniversityEditTextListener()
        handleHomeTownEditTextListener()
    }

    private fun isValidInfo(): Boolean {
        return edtRegisterUserName.text.isNotEmpty() &&
                edtRegisterUniversity.text.isNotEmpty() &&
                edtRegisterHomeTown.text.isNotEmpty()
    }

    private fun setActiveNextButton() {
        btnRegisterNext.isEnabled = true
        btnRegisterNext.setBackgroundResource(R.drawable.bg_active_register_next_button)
    }

    private fun setInactiveNextButton() {
        btnRegisterNext.isEnabled = false
        btnRegisterNext.setBackgroundResource(R.drawable.bg_inactive_register_next_button)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun handleOnTouchScreenListener() {
        rlRegister?.setOnTouchListener { it, _ ->
            it.clearFocus()
            this.hideSoftKeyboard()
            true
        }
    }

    private fun handleAvatarImageListener() {
        imgRegisterAvatar.setOnClickListener {
            pickFromGallery()
        }
    }

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = getString(R.string.image_intent_type)
        val mimeTypes = arrayOf(
            getString(R.string.image_intent_type_jpeg),
            getString(R.string.image_intent_type_png),
            getString(R.string.image_intent_type_jpg)
        )
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    // Handle event user name edit text
    private fun handleUserNameEditTextListener() {
        edtRegisterUserName.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (isValidInfo()) {
                    setActiveNextButton()
                } else {
                    setInactiveNextButton()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })
    }

    // Handle event university edit text
    private fun handleUniversityEditTextListener() {
        edtRegisterUniversity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (isValidInfo()) {
                    setActiveNextButton()
                } else {
                    setInactiveNextButton()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })
    }

    // Handle event home town edit text
    private fun handleHomeTownEditTextListener() {
        edtRegisterHomeTown.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (isValidInfo()) {
                    setActiveNextButton()
                } else {
                    setInactiveNextButton()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })
    }
}
