package com.asiantech.intern20summer1.w4.fragment

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w4.account.User
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.`at-sonnguyen`.fragment_register.*
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class SignUpFragment : Fragment() {

    var emailText = ""
    var passwordText = ""
    var checkFullName = false
    var checkRetypePassword = false
    var checkPhoneNumber = false

    companion object {
        private const val CAMERA_REQUEST_CODE = 111
        private const val GALLERY_REQUEST_CODE = 112
        private const val KEY_VALUE = "data"
        private const val PHONE_NUMBER_LENGTH=10
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        handleFullNameEditText()
        handleEmailEditText()
        handleSignUpPasswordEditText()
        handlePhoneNumberEditText()
        handleRetypePasswordEditText()
        setEnableRegisterButton()
        handleListenerAvatarImageView()
        handleListenerRegisterButton()
        handleBackButtonListener()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    private fun handleEmailEditText() {
        edtRegisterEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (isValidEmail(p0.toString())) {
                    edtRegisterEmail.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    emailText = p0.toString()
                } else {
                    edtRegisterEmail.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    emailText = ""
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun handleSignUpPasswordEditText() {
        edtRegisterPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (isValidPassword(p0.toString())) {
                    edtRegisterPassword.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    passwordText = p0.toString()
                } else {
                    edtRegisterPassword.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    passwordText = ""
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun handleFullNameEditText() {
        edtRegisterFullName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isEmpty()) {
                    edtRegisterFullName.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    checkFullName = false
                } else {
                    edtRegisterFullName.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    checkFullName = true
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun handleBackButtonListener() {
        val fragmentManager = activity?.supportFragmentManager
        btnBack.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }
    private fun handlePhoneNumberEditText() {
        edtRegisterPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (isValidPhoneNumber(p0.toString())) {
                    edtRegisterPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    checkPhoneNumber = true
                } else {
                    edtRegisterPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    checkPhoneNumber = false
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun handleRetypePasswordEditText() {
        edtRegisterRetypePassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (isValidPassword(p0.toString()) && p0.toString() == passwordText) {
                    checkRetypePassword = true
                    edtRegisterRetypePassword.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    setEnableRegisterButton()
                } else {
                    edtRegisterRetypePassword.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    checkRetypePassword = false
                    setEnableRegisterButton()
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }
    private fun handleListenerAvatarImageView() {
        imgAvatar.setOnClickListener {
            showListAlertDialog()
        }
    }
    private fun setEnableRegisterButton() {
        btnRegister.isEnabled =
            (isValidEmail(emailText) && isValidPassword(passwordText) && checkFullName
                    && checkPhoneNumber && checkRetypePassword)
    }
    private fun showListAlertDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Set avatar")
        val items = arrayOf("Gallery", "Camera")
        builder.setItems(items) { _, which ->
            when (which) {
                0 -> { openGallery() }
                1 -> { captureImage() }
            }
        }
        val dialog = builder.create()
        dialog.show()
    }
    private fun captureImage() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        imgAvatar.scaleType = ImageView.ScaleType.CENTER_CROP
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> if (data != null) {
                if (resultCode == RESULT_OK) {
                    (data.extras?.get("data") as Bitmap?)?.let {
                        getImageUri(it)?.let { it ->
                            cropImage(it)
                        }
                    }
                }
            }
            GALLERY_REQUEST_CODE -> if (resultCode == RESULT_OK && data != null) {
                data.data?.let {
                    cropImage(it)
                }
            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result: CropImage.ActivityResult = CropImage.getActivityResult(data)
                if (resultCode == RESULT_OK) {
                    val resultUri: Uri = result.uri
                    imgAvatar.setImageURI(resultUri)
                }
            }
        }
    }

    private fun getImageUri(inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(activity?.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }
    private fun cropImage(uri: Uri?) {
        context?.let { context ->
            CropImage.activity(uri)
                .setAspectRatio(1, 1)
                .start(context, this)
        }
    }

    private fun handleListenerRegisterButton() {
        btnRegister.setOnClickListener {
            val user = User(
                edtRegisterFullName.text.toString(),
                edtRegisterEmail.text.toString(),
                edtRegisterPhoneNumber.text.toString(),
                edtRegisterPassword.text.toString()
            )
            val bundle = Bundle()
            bundle.putSerializable(KEY_VALUE, user)
            val signInFragment = SignInFragment()
            val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
            fragmentTransaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            signInFragment.arguments = bundle
            fragmentTransaction?.replace(R.id.llFragment, signInFragment)
            fragmentManager?.popBackStack()
            fragmentTransaction?.commit()
        }
    }

    private fun openGallery() {
        val intentImage = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intentImage.type = "image/*"
        startActivityForResult(intentImage, GALLERY_REQUEST_CODE)
    }
    fun isValidEmail(string: String) =
        android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches()

    private fun isValidPassword(string: String): Boolean {
        if (string.length !in 8..16) {
            return false
        } else {
            for (i in string.indices) {
                if (string[i].isDigit()) return true
            }
        }
        return false
    }

    private fun isValidPhoneNumber(string: String): Boolean {
        if (string.length != PHONE_NUMBER_LENGTH) {
            return false
        }
        for (i in string.indices) {
            if (!string[i].isDigit()) {
                return false
            }
        }
        return true
    }
}
