package com.asiantech.intern20summer1.week4.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.model.User
import com.asiantech.intern20summer1.week4.other.*
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.`at-longphan`.fragment_sign_up.*
import java.io.ByteArrayOutputStream

class SignUpFragment : Fragment() {
    companion object {
        private const val KEY_IMAGE = "data"
        private const val HUNDRED = 100
        var userRegister = User()
        var checkFullName = false
        var checkEmail = false
        var checkMobileNumber = false
        var checkPassword = false
        var checkConfirmPassword = false
        var checkSignUp = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RequestCode.PICK_IMAGE_REQUEST -> if (resultCode == Activity.RESULT_OK) cropImageGallery(data)
            RequestCode.OPEN_CAMERA_REQUEST -> if (resultCode == Activity.RESULT_OK) cropImageCamera(data)
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> showImage(data)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RequestCode.OPEN_CAMERA_REQUEST -> {
                if (checkCameraPermissions()) {
                    openCamera()
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.camera_permission_denied_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            RequestCode.PICK_IMAGE_REQUEST -> {
                if (checkStoragePermissions()) {
                    pickImage()
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.storage_permission_denied_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    internal var onRegisterSuccess: (user: User) -> Unit = {}

    private fun checkStoragePermissions(): Boolean {
        val permission = checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return permission == PackageManager.PERMISSION_GRANTED
    }

    private fun checkCameraPermissions(): Boolean {
        val permissionCamera = checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        )
        val permissionWrite = checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return (permissionCamera == PackageManager.PERMISSION_GRANTED
                && permissionWrite == PackageManager.PERMISSION_GRANTED)
    }

    private fun handleListener() {
        handleButtonBackListener()
        handleEditTextFullNameListener()
        handleEditTextEmailListener()
        handleEditTextMobileNumberListener()
        handleEditTextPasswordListener()
        handleEditTextConfirmPasswordListener()
        handleImageViewAvatarListener()
        handleButtonSignUpListener()
    }

    private fun handleButtonBackListener() {
        imgBtnBack.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun handleEditTextFullNameListener() {
        edtFullName.addTextChangedListener {
            val fullName = it.toString()
            userRegister.fullName = fullName
            checkFullName = fullName.isNotBlank()
            checkSignUpButton()
        }
    }

    private fun handleEditTextEmailListener() {
        edtEmail.addTextChangedListener {
            val email = it.toString()
            userRegister.email = email
            checkEmail = isValidEmail(email)
            checkSignUpButton()
        }
    }

    private fun handleEditTextMobileNumberListener() {
        edtMobileNumber.addTextChangedListener {
            val mobileNumber = it.toString()
            userRegister.mobileNumber = mobileNumber
            checkMobileNumber = isValidMobileNumber(mobileNumber)
            checkSignUpButton()
        }
    }

    private fun handleEditTextPasswordListener() {
        edtPassword.addTextChangedListener {
            val password = it.toString()
            userRegister.password = password
            checkPassword = isValidPassword(password)
            checkSignUpButton()
        }
    }

    private fun handleEditTextConfirmPasswordListener() {
        edtConfirmPassword.addTextChangedListener {
            val confirmPassword = it.toString()
            checkConfirmPassword = edtPassword.text.toString() == confirmPassword
            checkSignUpButton()
        }
    }

    private fun handleImageViewAvatarListener() {
        imgAvatar.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle(getString(R.string.title_pick_avatar_dialog))
            val optionList = arrayOf(
                getString(R.string.pick_in_gallery_option),
                getString(R.string.open_camera_option)
            )
            dialogBuilder.setItems(optionList) { _, which ->
                when (which) {
                    0 -> {
                        if (checkStoragePermissions()) pickImage()
                        else requestStoragePermissions()
                    }
                    1 -> {
                        if (checkCameraPermissions()) openCamera()
                        else requestCameraPermissions()
                    }
                }
            }
            val dialog = dialogBuilder.create()
            dialog.show()
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
            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, this)
            userRegister.avatarUri = this.toString()
            imgAvatar.setImageBitmap(bitmap)
        }
    }

    private fun handleCropImage(uri: Uri) {
        context?.let {
            CropImage.activity(uri)
                .setAspectRatio(1, 1)
                .start(it, this)
        }
    }

    private fun getImageUri(inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, HUNDRED, bytes)
        val path =
            MediaStore.Images.Media.insertImage(
                context?.contentResolver,
                inImage,
                getString(R.string.image_path_title),
                null
            )
        return Uri.parse(path)
    }

    private fun requestStoragePermissions() {
        requestPermissions(
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            RequestCode.PICK_IMAGE_REQUEST
        )
    }

    private fun requestCameraPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), RequestCode.OPEN_CAMERA_REQUEST
        )
    }

    private fun handleButtonSignUpListener() {
        btnSignUp.setOnClickListener {
            onRegisterSuccess(userRegister)
            Toast.makeText(context, getString(R.string.text_sign_up_success), Toast.LENGTH_SHORT).show()
            fragmentManager?.popBackStack()
        }
    }

    private fun checkSignUpButton() {
        checkSignUp =
            checkFullName && checkEmail && checkMobileNumber && checkPassword && checkConfirmPassword
        toggleSignUpButton()
        btnSignUp.isEnabled = checkSignUp
    }

    private fun toggleSignUpButton() {
        if (checkSignUp) {
            btnSignUp.setBackgroundResource(R.drawable.bg_button_enable)
        } else {
            btnSignUp.setBackgroundResource(R.drawable.bg_button_unable)
        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = IntentType.IMAGE
        startActivityForResult(intent, RequestCode.PICK_IMAGE_REQUEST)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, RequestCode.OPEN_CAMERA_REQUEST)
    }
}
