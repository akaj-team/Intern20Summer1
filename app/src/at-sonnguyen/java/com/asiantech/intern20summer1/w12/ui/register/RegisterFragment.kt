package com.asiantech.intern20summer1.w12.ui.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w12.data.model.UserRegister
import com.asiantech.intern20summer1.w12.data.source.repository.RegisterRepository
import com.asiantech.intern20summer1.w12.extension.isValidEmail
import com.asiantech.intern20summer1.w12.extension.isValidPassword
import com.asiantech.intern20summer1.w12.ui.login.LoginFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-sonnguyen`.w12_fragment_register.*

class RegisterFragment : Fragment() {
    private var emailText = ""
    private var fullNameText = ""
    private var passwordText = ""
    private var checkFullName = false
    private var viewModel: RegisterVMContact? = null

    companion object {
        internal fun newInstance() = RegisterFragment()
        internal const val KEY_VALUE_EMAIL = "email_key"
        internal const val KEY_VALUE_PASSWORD = "password_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = RegisterViewModel(RegisterRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w12_fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        handleEmailEditTextListener()
        handleFullNameEditTextListener()
        handlePasswordEditTextListener()
        setEnableRegisterButton()
        handleRegisterButtonListener()
    }

    private fun setEnableRegisterButton() {
        btnRegister.isEnabled =
            isValidEmail(emailText) && isValidPassword(passwordText) && checkFullName
    }

    private fun handleEmailEditTextListener() {
        edtEmailRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (isValidEmail(p0.toString())) {
                    edtEmailRegister.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    emailText = p0.toString()
                    setEnableRegisterButton()
                } else {
                    edtEmailRegister.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    emailText = ""
                    setEnableRegisterButton()
                }
            }
        })
    }

    private fun handleRegisterButtonListener() {
        btnRegister.setOnClickListener {
            progressBarRegister.visibility = View.VISIBLE
            viewModel?.register(UserRegister(emailText, passwordText, fullNameText))
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    if (it.isSuccessful) {
                        Toast.makeText(
                            activity,
                            getString(R.string.w12_register_success_toast),
                            Toast.LENGTH_SHORT
                        ).show()
                        sendDataToLoginFragment()
                    } else {
                        Toast.makeText(
                            activity,
                            getString(R.string.w12_register_failure_toast),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    progressBarRegister.visibility = View.INVISIBLE
                }, {
                    // No-ops
                })
        }
    }

    private fun sendDataToLoginFragment() {
        val loginFragment = LoginFragment.newInstance(emailText, passwordText)
        val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
        fragmentTransaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction?.replace(R.id.frameLayoutMain, loginFragment)
        fragmentManager?.popBackStack()
        fragmentTransaction?.commit()
    }

    private fun handlePasswordEditTextListener() {
        edtPasswordRegister.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (isValidPassword(p0.toString())) {
                    edtPasswordRegister.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    passwordText = p0.toString()
                    setEnableRegisterButton()
                } else {
                    edtPasswordRegister.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    passwordText = ""
                    setEnableRegisterButton()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun handleFullNameEditTextListener() {
        edtFullNameRegister.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isEmpty()) {
                    edtFullNameRegister.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_error,
                        0
                    )
                    fullNameText = ""
                    checkFullName = false
                    setEnableRegisterButton()
                } else {
                    edtFullNameRegister.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.icon_tick,
                        0
                    )
                    fullNameText = p0.toString()
                    checkFullName = true
                    setEnableRegisterButton()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }
}
