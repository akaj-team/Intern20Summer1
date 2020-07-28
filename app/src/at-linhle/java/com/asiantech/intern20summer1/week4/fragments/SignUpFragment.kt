package com.asiantech.intern20summer1.week4.fragments

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
import android.provider.MediaStore.Images
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.extensions.hideSoftKeyboard
import com.asiantech.intern20summer1.week4.models.User
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.`at-linhle`.fragment_sign_up.*
import java.io.ByteArrayOutputStream
import java.util.regex.Pattern

class SignUpFragment : Fragment() {

    // Interface to pass data
    internal var onRegisterSuccess: (user: User) -> Unit = {}

    // At least 1 digit
    private val passwordPattern = Pattern.compile("""^(?=.*[0-9]).{8,16}$""")

    // Must have 10 digits
    private val phonePattern = Pattern.compile("""^([0-9]){10}$""")
    private var imageUri: String? = ""
    private var checkCameraStatus = false

    companion object {
        private const val KEY_IMAGE = "data"
        private const val OPEN_CAMERA_REQUEST = 1
        private const val PICK_IMAGE_REQUEST = 2
        private const val KEY_IMAGE_GALLERY = "image/*"
        private const val HUNDRED = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClickingArrowLeft()
        handleSignUpFullNameTextChanged()
        handleSignUpPhoneTextChanged()
        handleSignUpEmailTextChanged()
        handleSignUpPasswordTextChanged()
        handleSignUpConfirmPasswordTextChanged()
        handleOnTouchScreen()
        handleClickingRegisterButton()
        handleClickingArrowLeft()
        handleListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                OPEN_CAMERA_REQUEST -> {
                    if (!checkStoragePermissions()) {
                        makeStorageRequest()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == OPEN_CAMERA_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkCameraStatus = true
                if (!checkStoragePermissions()) {
                    makeStorageRequest()
                } else {
                    openCamera()
                }
            }
        }
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (!checkCameraStatus) {
                    openStorage()
                } else {
                    openCamera()
                }
            }
        }
    }

    private fun handleClickingArrowLeft() {
        imgArrowLeft.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun isSignUpFullNameValid(fullName: String) = fullName != ""

    private fun isSignUpPhoneValid(phone: String) = phonePattern.matcher(phone).matches()

    private fun isSignUpPasswordValid(password: String) =
        passwordPattern.matcher(password).matches()

    private fun isSignUpEmailValid(email: String) =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isSignUpConfirmPassword(confirmPassword: String) =
        passwordPattern.matcher(confirmPassword).matches()
                && confirmPassword == edtSignUpPassword.text.toString()

    @SuppressLint("ClickableViewAccessibility")
    private fun handleOnTouchScreen() {
        llSignUpMain?.setOnTouchListener { it, _ ->
            it.clearFocus()
            it.hideSoftKeyboard()
            true
        }
    }

    // Check all edit text correct validate
    private fun isCorrectFormat(
        fullName: String,
        phone: String,
        password: String,
        email: String,
        confirmPassword: String
    ) = isSignUpFullNameValid(fullName) && isSignUpPhoneValid(phone)
            && isSignUpEmailValid(email) && isSignUpPasswordValid(password)
            && isSignUpConfirmPassword(confirmPassword)

    private fun handleSignUpFullNameTextChanged() {
        edtSignUpFullName.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormat(
                p0.toString(),
                edtSignUpPhone.text.toString(),
                edtSignUpPassword.text.toString(),
                edtSignUpEmail.text.toString(),
                edtSignUpConfirmPassword.text.toString()
            )
        })
    }

    private fun handleSignUpPhoneTextChanged() {
        edtSignUpPhone.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormat(
                edtSignUpFullName.text.toString(),
                p0.toString(),
                edtSignUpPassword.text.toString(),
                edtSignUpEmail.text.toString(),
                edtSignUpConfirmPassword.text.toString()
            )
        })
    }

    private fun handleSignUpEmailTextChanged() {
        edtSignUpEmail.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormat(
                edtSignUpFullName.text.toString(),
                edtSignUpPhone.text.toString(),
                edtSignUpPassword.text.toString(),
                p0.toString(),
                edtSignUpConfirmPassword.text.toString()
            )
        })
    }

    private fun handleSignUpPasswordTextChanged() {
        edtSignUpPassword.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormat(
                edtSignUpFullName.text.toString(),
                edtSignUpPhone.text.toString(),
                p0.toString(),
                edtSignUpEmail.text.toString(),
                edtSignUpConfirmPassword.text.toString()
            )
        })
    }

    private fun handleSignUpConfirmPasswordTextChanged() {
        edtSignUpConfirmPassword.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister.isEnabled = isCorrectFormat(
                edtSignUpFullName.text.toString(),
                edtSignUpPhone.text.toString(),
                edtSignUpPassword.text.toString(),
                edtSignUpEmail.text.toString(),
                p0.toString()
            )
        })
    }

    // Pass data when click button register
    private fun handleClickingRegisterButton() {
        val user = User()
        btnRegister.setOnClickListener {
            user.avatar = imageUri
            user.email = edtSignUpEmail.text.toString()
            user.fullName = edtSignUpFullName.text.toString()
            user.password = edtSignUpPassword.text.toString()
            user.phone = edtSignUpPhone.text.toString()
            onRegisterSuccess(user)
            fragmentManager?.popBackStack()
        }
    }

    private fun cropImageCamera(data: Intent?) {
        (data?.extras?.get(KEY_IMAGE) as? Bitmap)?.let {
            getImageUri(it)?.let { uri -> handleCropImage(uri) }
        }
    }

    private fun cropImageGallery(data: Intent?) {
        data?.data?.let {
            handleCropImage(it)
        }
    }

    private fun showImage(data: Intent?) {
        CropImage.getActivityResult(data).uri?.apply {
            val bitmap = Images.Media.getBitmap(activity?.contentResolver, this)
            imageUri = this.toString()
            imgSignUpProfile.setImageBitmap(bitmap)
        }
    }

    private fun handleCropImage(uri: Uri) {
        context?.let {
            CropImage.activity(uri).setAspectRatio(1, 1).start(it, this)
        }
    }

    private fun getImageUri(inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, HUNDRED, bytes)
        val path =
            Images.Media.insertImage(
                context?.contentResolver,
                inImage,
                resources.getString(R.string.sign_up_fragment_image_profile_title),
                null
            )
        return Uri.parse(path)
    }

    private fun checkStoragePermissions() = checkSelfPermission(
        requireContext(),
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    private fun checkCameraPermissions(): Boolean {
        val permissionCamera = checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        val permissionWrite =
            checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return (permissionCamera == PackageManager.PERMISSION_GRANTED
                && permissionWrite == PackageManager.PERMISSION_GRANTED)
    }

    private fun makeStorageRequest() {
        requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), PICK_IMAGE_REQUEST
        )
    }

    private fun makeCameraRequest() {
        requestPermissions(arrayOf(Manifest.permission.CAMERA), OPEN_CAMERA_REQUEST)
    }

    private fun openStorage() {
        val intentImage = Intent(Intent.ACTION_PICK, Images.Media.EXTERNAL_CONTENT_URI)
        intentImage.type = KEY_IMAGE_GALLERY
        startActivityForResult(intentImage, PICK_IMAGE_REQUEST)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, OPEN_CAMERA_REQUEST)
    }

    private fun handleListener() {
        imgSignUpProfile.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle(R.string.sign_up_fragment_choose_option_dialog)
            val optionList = arrayOf(
                resources.getString(R.string.sign_up_fragment_gallery),
                resources.getString(R.string.sign_up_fragment_camera)
            )
            dialogBuilder.setItems(optionList) { _, which ->
                when (which) {
                    0 -> {
                        if (checkStoragePermissions()) {
                            openStorage()
                        } else {
                            makeStorageRequest()
                        }
                    }
                    1 -> {
                        if (checkCameraPermissions()) {
                            openCamera()
                        } else {
                            makeCameraRequest()
                        }
                    }
                }
            }
            // Create and show the alert dialog
            val dialog = dialogBuilder.create()
            dialog.show()
        }
    }
}
