package com.asiantech.intern20summer1.week4.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.model.User
import com.asiantech.intern20summer1.week4.other.*
import kotlinx.android.synthetic.`at-longphan`.fragment_sign_up.*

class SignUpFragment : Fragment() {
    companion object {
        var userRegister = User()
        var image_uri: Uri? = null
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
            RequestCode.PICK_IMAGE_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    val uri: Uri? = data?.data
                    imgAvatar.setImageURI(uri)
                    userRegister.avatarUri = uri.toString()
                }
            }
            RequestCode.OPEN_CAMERA_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    imgAvatar.setImageURI(image_uri)
                    userRegister.avatarUri = image_uri.toString()
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

    private fun makeStorageRequest() {
        this.activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                RequestCode.STORAGE_REQUEST
            )
        }
    }

    private fun makeCameraRequest() {
        this.activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                RequestCode.OPEN_CAMERA_REQUEST
            )
        }
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
                        else makeStorageRequest()
                    }
                    1 -> {
                        if (checkCameraPermissions()) openCamera()
                        else makeCameraRequest()
                    }
                }
            }
            val dialog = dialogBuilder.create()
            dialog.show()
        }
    }

    private fun handleButtonSignUpListener() {
        btnSignUp.setOnClickListener {
            onRegisterSuccess(userRegister)
            Toast.makeText(context, getString(R.string.text_sign_up_success), Toast.LENGTH_SHORT)
                .show()
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
        val intent = Intent()
        intent.type = IntentType.IMAGE.string
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, IntentTitle.PICK_IMAGE_TITLE.string),
            RequestCode.PICK_IMAGE_REQUEST
        )
    }

    private fun openCamera() {
        val values = ContentValues()
        image_uri =
            context?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(
            Intent.createChooser(intent, IntentTitle.OPEN_CAMERA_TITLE.string),
            RequestCode.OPEN_CAMERA_REQUEST
        )
    }
}
