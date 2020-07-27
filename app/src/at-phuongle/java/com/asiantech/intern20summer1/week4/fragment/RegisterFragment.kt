package com.asiantech.intern20summer1.week4.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.activity.SignInActivity
import com.asiantech.intern20summer1.week4.model.User
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.`at-phuongle`.fragment_register.*


class RegisterFragment : Fragment() {
    private var avatarUri: Uri? = null

    companion object {
        const val GALLERY_REQUEST_CODE = 1000
        const val CAMERA_REQUEST_CODE = 1001
        const val REGISTER_DATA_KEY = "register_data"

        fun newInstance() = RegisterFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    data?.data?.let { uri ->
                        launchImageCrop(uri)
                    }
                }

                GALLERY_REQUEST_CODE -> {
                    data?.data?.let { uri ->
                        launchImageCrop(uri)
                    }
                }

                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val result = CropImage.getActivityResult(data)
                    imgRegisterAvatar.setImageURI(result.uri)
                    avatarUri = result.uri
                }

                CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE -> {
                    Toast.makeText(
                        context,
                        getString(R.string.toast_crop_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(
                        activity as SignInActivity,
                        "Camera Permission is Required to use camera.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            GALLERY_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery()
                } else {
                    Toast.makeText(
                        activity as SignInActivity,
                        "Gallery Permission is Required to use camera.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun initListener() {
        handleRegisterAvatar()
        handleBackButton()
        (activity as SignInActivity).handleFullNameEditText(edtRegisterFullName, btnRegister)
        (activity as SignInActivity).handleEmailEditText(edtRegisterEmail, btnRegister)
        (activity as SignInActivity).handleMobileEditText(edtRegisterMobile, btnRegister)
        (activity as SignInActivity).handlePasswordEditText(edtRegisterPassword, btnRegister)
        (activity as SignInActivity).handleConfirmPasswordEditText(
            edtRegisterPassword,
            edtRegisterConfirmPassword,
            btnRegister
        )
        handleRegisterButton()
    }

    private fun handleBackButton() {
        imgBtnBack.setOnClickListener {
            (activity as SignInActivity).replaceFragment(
                LoginFragment.newInstance(),
                false
            )
        }
    }

    private fun handleRegisterAvatar() {
        imgRegisterAvatar.setOnClickListener {
            handleListAlertDialog()
        }
    }

    private fun handleListAlertDialog() {
        // Setup the alert builder
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.register_button_alert_title))

        // Add a list
        val options = arrayOf(
            getString(R.string.register_button_alert_option_camera),
            getString(R.string.register_button_alert_option_gallery)
        )
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> { /* Camera */
                    askCameraPermissions()
                }
                1 -> { /* Gallery   */
                    askGalleryPermissions()
                }
            }
        }

        // Create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }

    private fun requestCameraPermission() {
        requestPermissions(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), CAMERA_REQUEST_CODE
        )
    }

    private fun requestReadPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), GALLERY_REQUEST_CODE
        )
    }

    private fun askCameraPermissions() {
        val cameraPermission = checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
        val storagePermission =
            checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (cameraPermission == PackageManager.PERMISSION_GRANTED &&
            storagePermission == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else {
            requestCameraPermission()
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    private fun askGalleryPermissions() {
        val readPermission =
            checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)

        if (readPermission == PackageManager.PERMISSION_GRANTED) {
            pickFromGallery()
        } else {
            requestReadPermission()
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

    private fun launchImageCrop(uri: Uri) {
        context?.let {
            CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(it, this)
        }
    }

    private fun handleRegisterButton() {
        btnRegister.setOnClickListener {
            val user = User(
                edtRegisterFullName.text.toString(),
                avatarUri.toString(),
                edtRegisterEmail.text.toString(),
                edtRegisterMobile.text.toString(),
                edtRegisterPassword.text.toString()
            )

            val fragment = LoginFragment()
            val bundle = Bundle()
            bundle.putParcelable(REGISTER_DATA_KEY, user)
            fragment.arguments = bundle

            (activity as SignInActivity).replaceFragment(fragment, false)
        }
    }
}