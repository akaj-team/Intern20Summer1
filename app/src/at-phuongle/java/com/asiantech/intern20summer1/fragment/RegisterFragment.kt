package com.asiantech.intern20summer1.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.SignInActivity
import kotlinx.android.synthetic.`at-phuongle`.fragment_register.*

class RegisterFragment : Fragment() {
    companion object {
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
    }

    private fun handleRegisterAvatar() {
        imgRegisterAvatar.setOnClickListener {
            handleListAlertDialog()
        }
    }

    private fun handleBackButton() {
        imgBtnBack.setOnClickListener {
            (activity as SignInActivity).replaceFragment(LoginFragment.newInstance(), true)
        }
    }

    private fun handleListAlertDialog() {
        // Setup the alert builder
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Set your image: ")

        // Add a list
        val options = arrayOf("Take a picture", "Choose from gallery")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> { /* Gallery */
                }
                1 -> { /* Camera   */
                }
            }
        }

        // Create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }
}
