package com.asiantech.intern20summer1.week10.fragment

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week10.api.RetrofitClient
import com.asiantech.intern20summer1.week10.model.User
import com.asiantech.intern20summer1.week10.model.UserRegister
import com.asiantech.intern20summer1.week4.other.isValidEmail
import com.asiantech.intern20summer1.week4.other.isValidPassword
import kotlinx.android.synthetic.`at-longphan`.fragment_sign_up_w10.*
import retrofit2.Call
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

class SignUpFragment : Fragment() {

    private var userRegister = UserRegister()
    private var isFullNameValid = false
    private var isEmailValid = false
    private var isPasswordValid = false
    private var isConfirmPasswordValid = false
    private var isSignUpEnabled = false
    internal var onRegisterSuccess: (user: UserRegister) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up_w10, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleListener()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun handleListener() {
        handleButtonBackListener()
        handleEditTextFullNameListener()
        handleEditTextEmailListener()
        handleEditTextPasswordListener()
        handleEditTextConfirmPasswordListener()
        handleButtonSignUpListener()
    }

    private fun handleButtonBackListener() {
        imgBtnBack?.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun handleEditTextFullNameListener() {
        edtFullNameW10?.addTextChangedListener {
            val fullName = it.toString()
            userRegister.fullName = fullName
            isFullNameValid = fullName.isNotBlank()
            checkSignUpButton()
        }
    }

    private fun handleEditTextEmailListener() {
        edtEmailW10?.addTextChangedListener {
            val email = it.toString()
            userRegister.email = email
            isEmailValid = isValidEmail(email)
            checkSignUpButton()
        }
    }

    private fun handleEditTextPasswordListener() {
        edtPasswordW10?.addTextChangedListener {
            val password = it.toString()
            userRegister.password = password
            isPasswordValid = isValidPassword(password)
            checkSignUpButton()
        }
    }

    private fun handleEditTextConfirmPasswordListener() {
        edtConfirmPasswordW10?.addTextChangedListener {
            val confirmPassword = it.toString()
            isConfirmPasswordValid = edtPasswordW10.text.toString() == confirmPassword
            checkSignUpButton()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun handleButtonSignUpListener() {
        btnSignUpW10?.setOnClickListener {
            val fullName = edtFullNameW10.text.toString()
            val email = edtEmailW10.text.toString()
            val password = edtPasswordW10.text.toString()

            val callApi =
                RetrofitClient.createUserService()
                    ?.createUser(UserRegister(email, password, fullName))

            val builder = AlertDialog.Builder(context)
            builder.setView(R.layout.progress_dialog_loading)
            val progressDialogLoading = builder.create()
            progressDialogLoading?.show()

            callApi?.enqueue(object : retrofit2.Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    progressDialogLoading?.dismiss()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_no_network_connection),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    progressDialogLoading?.dismiss()
                    when (response.code()) {
                        HttpsURLConnection.HTTP_OK -> {
                            onRegisterSuccess(userRegister)
                            Toast.makeText(
                                context,
                                getString(R.string.text_sign_up_success),
                                Toast.LENGTH_SHORT
                            ).show()
                            fragmentManager?.popBackStack()
                        }
                        else -> {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.text_error_occurred),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            })
        }
    }

    private fun checkSignUpButton() {
        isSignUpEnabled =
            isFullNameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid
        toggleSignUpButton()
        btnSignUpW10?.isEnabled = isSignUpEnabled
    }

    private fun toggleSignUpButton() {
        if (isSignUpEnabled) {
            btnSignUpW10?.setBackgroundResource(R.drawable.bg_button_enable)
        } else {
            btnSignUpW10?.setBackgroundResource(R.drawable.bg_button_unable)
        }
    }
}
