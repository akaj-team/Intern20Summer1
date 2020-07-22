package com.asiantech.intern20summer1.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.models.User
import com.asiantech.intern20summer1.views.HomeActivity
import kotlinx.android.synthetic.`at-linhle`.fragment_signin.*
import java.util.regex.Pattern

class SignInFragment : Fragment() {

    private val passwordPattern = Pattern.compile("""^(?=.*[0-9]).{8,16}$""")
    private var user = User("", "", "", "", "")

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
            if (edtEmail.text.toString() == user.email
                && edtPassword.text.toString() == user.password
            ) {
                val intent = Intent(activity, HomeActivity::class.java)
                intent.putExtra("user", user)
                activity?.startActivity(intent)
                activity?.finish()
            } else {
                displayAlert()
            }
        }
    }

    private fun handleEmailTextChanged() {
        edtEmail.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
            btnLogin.isEnabled = isCorrectFormat(p0.toString(), edtPassword.text.toString())
        })
    }

    private fun handlePasswordTextChanged() {
        edtPassword.addTextChangedListener(onTextChanged = { p0: CharSequence?, _, _, _ ->
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
            transaction?.add(R.id.flContainer, SignUpFragment().apply {
                onRegisterSuccess = { user ->
                    this@SignInFragment.user = user
                    this@SignInFragment.edtEmail.setText(user.email)
                    this@SignInFragment.edtPassword.setText(user.password)
                }
            })
                ?.addToBackStack(null)
                ?.hide(this)
                ?.commit()
        }
    }
}
