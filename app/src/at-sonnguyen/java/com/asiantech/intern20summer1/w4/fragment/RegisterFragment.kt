package com.asiantech.intern20summer1.w4.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
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
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w4.account.User
import com.asiantech.intern20summer1.w4.activity.HomeActivity.Companion.KEY_VALUE
import com.asiantech.intern20summer1.w4.extension.isValidEmail
import com.asiantech.intern20summer1.w4.extension.isValidPassword
import com.asiantech.intern20summer1.w4.extension.isValidPhoneNumber
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.`at-sonnguyen`.fragment_register.*
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION", "NAME_SHADOWING")
class RegisterFragment : Fragment() {

    private var emailText = ""
    private var passwordText = ""
    private var checkFullName = false
    private var checkRetypePassword = false
    private var checkPhoneNumber = false
    private var avatarUri = ""
    private var flag = false

    companion object {
        private const val CROP_IMAGE_HEIGHT = 1
        private const val CROP_IMAGE_WIDTH = 1
        private const val IMAGE_URI_QUALITY = 100
        private const val CAMERA_REQUEST_CODE = 111
        private const val GALLERY_REQUEST_CODE = 112
        internal const val PHONE_NUMBER_LENGTH = 10
        internal fun newInstance() = RegisterFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    private fun initListener() {
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    flag = true
                    if (!isGalleryAllow()) {
                        requestGalleryPermission()
                    }
                    if (isGalleryAllow()) {
                        openCamera()
                    }
                } else {
                    Toast.makeText(
                        activity,
                        resources.getString(R.string.permission_denied_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            GALLERY_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (!flag) {
                        openGallery()
                    } else {
                        openCamera()
                    }
                } else {
                    Toast.makeText(
                        activity,
                        resources.getString(R.string.permission_denied_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        imgAvatar.scaleType = ImageView.ScaleType.CENTER_CROP
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    if (!isGalleryAllow()) {
                        requestGalleryPermission()
                    } else {
                        (data?.extras?.get(KEY_VALUE) as? Bitmap)?.let {
                            getImageUri(it)?.let(this@RegisterFragment::cropImage)
                        }
                    }
                }
                GALLERY_REQUEST_CODE -> {
                    cropImage(data?.data)
                }
                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val result: CropImage.ActivityResult = CropImage.getActivityResult(data)
                    val resultUri: Uri = result.uri
                    avatarUri = resultUri.toString()
                    imgAvatar.setImageURI(resultUri)
                }
            }
        }
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
        imgBack.setOnClickListener {
            activity?.onBackPressed()
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
            isValidEmail(emailText) && isValidPassword(passwordText) && checkFullName
                    && checkPhoneNumber && checkRetypePassword
    }

    private fun showListAlertDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(resources.getString(R.string.register_fragment_set_avatar_dialog_list_title))
        val items = arrayOf(
            resources.getString(R.string.register_fragment_choose_set_avatar_dialog_from_gallery),
            resources.getString(R.string.register_fragment_choose_set_avatar_dialog_from_camera)
        )
        builder.setItems(items) { _, which ->
            when (which) {
                0 -> {
                    if (isGalleryAllow()) {
                        openGallery()
                    } else {
                        requestGalleryPermission()
                    }
                }
                1 -> {
                    if (isCameraAllow() && isGalleryAllow()) {
                        openCamera()
                    } else {
                        requestCameraPermission()
                    }
                }
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun requestCameraPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE
        )
    }

    private fun requestGalleryPermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            GALLERY_REQUEST_CODE
        )
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    private fun openGallery() {
        val intentImage =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intentImage, GALLERY_REQUEST_CODE)
    }

    private fun isCameraAllow() = checkSelfPermission(
        requireContext(),
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private fun isGalleryAllow() = checkSelfPermission(
        requireContext(),
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    private fun getImageUri(inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, IMAGE_URI_QUALITY, bytes)
        val path =
            MediaStore.Images.Media.insertImage(
                activity?.contentResolver,
                inImage,
                resources.getString(R.string.image_title),
                null
            )
        return Uri.parse(path)
    }

    private fun cropImage(uri: Uri?) {
        context?.let { context ->
            CropImage.activity(uri)
                .setAspectRatio(CROP_IMAGE_WIDTH, CROP_IMAGE_HEIGHT)
                .start(context, this)
        }
    }

    private fun handleListenerRegisterButton() {
        btnRegister.setOnClickListener {
            val user = User(
                edtRegisterFullName.text.toString(),
                edtRegisterEmail.text.toString(),
                edtRegisterPhoneNumber.text.toString(),
                edtRegisterPassword.text.toString(),
                avatarUri
            )
            val bundle = Bundle()
            bundle.putSerializable(KEY_VALUE, user)
            val signInFragment = LogInFragment()
            val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
            fragmentTransaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            signInFragment.arguments = bundle
            fragmentTransaction?.replace(R.id.llFragment, signInFragment)
            fragmentManager?.popBackStack()
            fragmentTransaction?.commit()
        }
    }
}
