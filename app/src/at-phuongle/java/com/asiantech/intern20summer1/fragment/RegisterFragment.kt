package com.asiantech.intern20summer1.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.SignInActivity
import com.asiantech.intern20summer1.model.User
import com.bumptech.glide.Glide
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

    private fun handleRegisterAvatar() {
        imgRegisterAvatar.setOnClickListener {
            handleListAlertDialog()
        }
    }

    private fun handleBackButton() {
        imgBtnBack.setOnClickListener {
            (activity as SignInActivity).replaceFragment(
                LoginFragment.newInstance(),
                false
            )
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

    private fun handleListAlertDialog() {
        // Setup the alert builder
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Set your image: ")

        // Add a list
        val options = arrayOf("Take a picture", "Choose from gallery")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> { /* Camera */
//                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
                }
                1 -> { /* Gallery   */
                    pickFromGallery()
                }
            }
        }

        // Create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    // Handle result of Avatar
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    data?.data?.let { uri ->
                        launchImageCrop(uri)
                    }
                }

                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val result = CropImage.getActivityResult(data)
                    Glide.with(this).load(result.uri).into(imgRegisterAvatar)
                }

                CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE -> {
                    Toast.makeText(
                        activity as SignInActivity,
                        "Crop error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun launchImageCrop(uri: Uri) {
        CropImage.activity(uri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(1, 1)
            .start(activity as SignInActivity)
    }
}
