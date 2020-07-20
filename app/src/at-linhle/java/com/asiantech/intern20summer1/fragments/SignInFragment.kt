package com.asiantech.intern20summer1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.extensions.textChangedListener
import kotlinx.android.synthetic.`at-linhle`.fragment_signin.*
import java.util.regex.Pattern


class SignInFragment : Fragment() {

    companion object {
        private val passwordPattern = Pattern.compile("""^(?=.*[0-9]).{8,16}$""")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEmailTextChanged()
        handlePasswordTextChanged()
        handleClickingLoginButton()
        handleClickingRegisterTextView()
    }

    private fun isEmailValid(email: String) =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isPasswordValid(password: String) = passwordPattern.matcher(password).matches()

    private fun isCorrectFormat(email: String, password: String) =
        isEmailValid(email) && isPasswordValid(password)

    private fun handleClickingLoginButton() {
        btnLogin.setOnClickListener {
            displayAlert()
        }
    }

    private fun handleEmailTextChanged() {
        edtEmail.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnLogin.isEnabled = isCorrectFormat(p0.toString(), edtPassword.text.toString())
        })
    }

    private fun handlePasswordTextChanged() {
        edtPassword.textChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnLogin.isEnabled = isCorrectFormat(edtEmail.text.toString(), p0.toString())
        })
    }

    private fun displayAlert() {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle("Incorrect")
                .setMessage("Email or Password is incorrect")
                .setPositiveButton(
                    "OK"
                ) { dialog, _ -> dialog.cancel() }.show()
        }
    }

    private fun handleClickingRegisterTextView() {
        tvRegister.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.flContainer, SignUpFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}
