package com.asiantech.intern20summer1.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.SignInActivity
import com.asiantech.intern20summer1.model.User
import kotlinx.android.synthetic.`at-phuongle`.fragment_register.*


class RegisterFragment : Fragment() {
    private var avatarUri: String = ""

    companion object {
        const val IMAGE_PICK_CODE = 1000
        const val CAMERA_REQUEST_CODE = 1001
        const val DATA_KEY = "register_data"

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
                avatarUri,
                edtRegisterEmail.text.toString(),
                edtRegisterMobile.text.toString(),
                edtRegisterPassword.text.toString()
            )

            val fragment = LoginFragment()
            val bundle = Bundle()
            bundle.putParcelable(DATA_KEY, user)
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
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
                }
                1 -> { /* Gallery   */
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        IMAGE_PICK_CODE
                    )
                }
            }
        }

        // Create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }

    // Handle result of Avatar
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            avatarUri = data?.data.toString()
            imgRegisterAvatar.setImageURI(data?.data)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            avatarUri = data?.data.toString()
            imgRegisterAvatar.setImageURI(data?.data)
        }
    }
}
