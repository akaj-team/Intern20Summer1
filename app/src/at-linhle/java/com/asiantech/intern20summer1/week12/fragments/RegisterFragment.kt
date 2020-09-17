package com.asiantech.intern20summer1.week12.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week12.fragments.LoginFragment.Companion.MAX_EMAIL_LENGTH
import com.asiantech.intern20summer1.week12.models.UserRegister
import com.asiantech.intern20summer1.week12.viewmodels.LoginViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-linhle`.fragment_register.*
import java.util.regex.Pattern

class RegisterFragment : Fragment() {

    companion object {
        private const val MAX_FULL_NAME_LENGTH = 64
        internal fun newInstance() = RegisterFragment()
    }

    internal var onRegisterSuccess: (email: String, password: String) -> Unit = { _, _ -> }
    private val passwordPattern = Pattern.compile("""^(?=.*).{8,16}$""")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClickingArrowBack()
        handleRegisterEmailTextChanged()
        handleRegisterFullNameTextChanged()
        handleRegisterPasswordTextChanged()
        handleClickingRegisterButton()
    }

    private fun isSignUpFullNameValid(fullName: String) = fullName.length <= MAX_FULL_NAME_LENGTH

    private fun isSignUpPasswordValid(password: String) =
        passwordPattern.matcher(password).matches()

    private fun isSignUpEmailValid(email: String) =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email)
            .matches() && email.length <= MAX_EMAIL_LENGTH

    private fun isCorrectFormat(
        fullName: String,
        email: String,
        password: String
    ) = isSignUpFullNameValid(fullName) && isSignUpEmailValid(email) && isSignUpPasswordValid(
        password
    )

    private fun handleRegisterFullNameTextChanged() {
        edtUserName?.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister?.isEnabled = isCorrectFormat(
                p0.toString(),
                edtEmail?.text.toString(),
                edtPassword?.text.toString()
            )
            setBackgroundButton()
        })
    }

    private fun handleRegisterEmailTextChanged() {
        edtEmail?.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister?.isEnabled = isCorrectFormat(
                edtUserName?.text.toString(),
                p0.toString(),
                edtPassword?.text.toString()
            )
            setBackgroundButton()
        })
    }

    private fun handleRegisterPasswordTextChanged() {
        edtPassword?.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnRegister?.isEnabled = isCorrectFormat(
                edtUserName?.text.toString(),
                edtEmail?.text.toString(),
                p0.toString()
            )
            setBackgroundButton()
        })
    }

    private fun setBackgroundButton() {
        if (btnRegister.isEnabled) {
            btnRegister?.setBackgroundResource(R.drawable.bg_button_enabled)
        } else {
            btnRegister?.setBackgroundResource(R.drawable.bg_button_disabled)
        }
    }

    private fun handleClickingArrowBack() {
        imgArrowLeft?.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun handleClickingRegisterButton() {
        val fullName = edtUserName.text.toString()
        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()
        btnRegister?.setOnClickListener {
            LoginViewModel().register(UserRegister(email, password, fullName))
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    if (it.isSuccessful) {
                        Toast.makeText(
                            activity,
                            getString(R.string.register_fragment_register_success),
                            Toast.LENGTH_SHORT
                        ).show()
                        onRegisterSuccess(email, password)
                        activity?.onBackPressed()
                    }else{
                        Log.d("TAG", "handleClickingRegisterButton: ${it.code()}")
                    }
                }, {
                    //No-op
                    Log.d("TAG", "handleClickingRegisterButton: ${it.message}")
                })
        }
    }
}
