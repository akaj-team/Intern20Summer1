package com.asiantech.intern20summer1.week4

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.hideSoftKeyboard
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.`at-hoangtran`.w4_sign_up_fragment.*
import java.io.ByteArrayOutputStream


@Suppress("DEPRECATION")
class SignUpFragment : Fragment() {
    companion object {
        internal var onRegisterSuccess: (user: User) -> Unit = {}
        private const val PICK_IMAGE_REQUEST = 2
        private const val OPEN_CAMERA_REQUEST = 1
        private const val STORAGE_REQUEST = 0
        private const val CAMERA_REQUEST = 3
        private const val KEY_IMAGE = "image/*"
        private const val KEY_DATA = "data"
        const val MOBILE_NUMBER_LENGTH = 10
    }

    var flag = false
    private var ava = ""
    var emailCheck = false
    var passCheck = false
    var confirmPassCheck = false
    var mobileCheck = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w4_sign_up_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEditText(edt_email)
        handleEditText(edt_mobile)
        handleEditText(edt_password)
        handleEditText(edt_confirm_password)
        handleListener()
        handleBtnRegister()
        handleBtnBack()
        hideKeyBoard()
    }

    private val user = User("", "", "", "", "")

    @SuppressLint("ClickableViewAccessibility")
    private fun hideKeyBoard() {
        ln_main_sign_up.setOnTouchListener { it, _ ->
            it.requestFocus()
            it.hideSoftKeyboard()
            true
        }
    }

    private fun isValidPassword(str: String): Boolean {
        val regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$".toRegex()
        return str.matches(regex)
    }

    private fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidConfirmPassword(pass: String) =
        (pass == edt_password.text.toString() && isValidPassword(pass))

    private fun isValidMobileNumber(mobile: String) = mobile.length == MOBILE_NUMBER_LENGTH

    private fun handleEditText(edt: EditText) {
        edt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val str = s.toString()
                when (edt) {
                    edt_email -> {
                        emailCheck = isValidEmail(str)
                    }
                    edt_password -> {
                        confirmPassCheck = isValidPassword(str)

                    }
                    edt_confirm_password -> {
                        passCheck = isValidConfirmPassword(str)
                    }
                    edt_mobile -> {
                        mobileCheck = isValidMobileNumber(str)
                    }
                }
                btn_register.isEnabled = emailCheck && mobileCheck && passCheck && confirmPassCheck
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun handleBtnRegister() {
        btn_register.setOnClickListener {
            user.email = edt_email.text.toString()
            user.mobile = edt_mobile.text.toString()
            user.pass = edt_password.text.toString()
            user.name = edt_name.text.toString()
            user.avatar = ava
            onRegisterSuccess(
                user
            )
            fragmentManager?.popBackStack()
        }
    }

    private fun handleBtnBack() {
        img_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                OPEN_CAMERA_REQUEST -> {
                    if (!checkStoragePermission()) {
                        requestStoragePermission()
                    } else {
                        cropImageCamera(data)
                    }
                }
                PICK_IMAGE_REQUEST -> {
                    cropImageGallery(data)
                }
                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    showImage(data)
                }
            }
        }
    }

    private fun handleListener() {
        img_avatar.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle("Choose an option:")
            val optionList = arrayOf("Gallery", "Camera")
            dialogBuilder.setItems(optionList) { _, which ->
                when (which) {
                    0 -> {
                        if (checkStoragePermission()) {
                            openGallery()
                        } else {
                            requestStoragePermission()
                        }
                    }
                    1 -> {
                        if (checkCameraPermission()) {
                            openCamera()
                        } else {
                            requestCameraPermission()
                        }
                    }
                }
            }
            val dialog = dialogBuilder.create()
            dialog.show()
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(
            cameraIntent,
            OPEN_CAMERA_REQUEST
        )
    }

    private fun openGallery() {
        val intentImage =
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
        intentImage.type =
            KEY_IMAGE
        startActivityForResult(
            intentImage,
            PICK_IMAGE_REQUEST
        )
    }

    private fun cropImageCamera(data: Intent?) {
        (data?.extras?.get(KEY_DATA) as? Bitmap)?.let {
            getImageUri(it)?.let { uri -> handleCropImage(uri) }
        }
    }

    private fun cropImageGallery(data: Intent?) {
        data?.data.let {
            it?.let { it1 -> handleCropImage(it1) }
        }
    }

    private fun showImage(data: Intent?) {
        CropImage.getActivityResult(data).uri.apply {
            if (this != null) {
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, this)
                ava = this.toString()
                img_avatar.setImageBitmap(bitmap)
            }
        }
    }

    private fun handleCropImage(uri: Uri) {
        context?.let { context ->
            CropImage.activity(uri)
                .setAspectRatio(1, 1)
                .start(context, this)
        }
    }

    private fun getImageUri(inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(
                context?.contentResolver,
                inImage,
                "Title",
                null
            )
        return Uri.parse(path)
    }

    private fun checkStoragePermission(): Boolean =
        checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    private fun checkCameraPermission(): Boolean =
        checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestStoragePermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            STORAGE_REQUEST
        )
    }

    private fun requestCameraPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                flag = true
                if (!checkStoragePermission()) {
                    requestStoragePermission()
                } else {
                    openCamera()
                }
            }
        }
        if (requestCode == STORAGE_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (!flag) {
                    openGallery()
                } else {
                    openCamera()
                }
            }
        }
    }
}
