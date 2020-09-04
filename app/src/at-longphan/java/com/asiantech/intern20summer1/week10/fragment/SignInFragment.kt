package com.asiantech.intern20summer1.week10.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week4.activity.HomeActivity
import com.asiantech.intern20summer1.week4.model.User
import com.asiantech.intern20summer1.week4.other.SignInActivityData
import com.asiantech.intern20summer1.week4.other.isValidEmail
import com.asiantech.intern20summer1.week4.other.isValidPassword
import kotlinx.android.synthetic.`at-longphan`.fragment_sign_in.*

class SignInFragment : Fragment() {

    private var userLogin = User()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in_w10, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleListeners()
    }

    private fun handleListeners() {
        handleEditTextEmailIdListener()
        handleEditTextPasswordListener()
        handleButtonSignInListener()
        handleTextViewRegisterNowListener()
    }

    private fun handleEditTextEmailIdListener() {
        edtEmailId.addTextChangedListener {
            btnSignIn.isEnabled =
                isValidEmail(it.toString()) && isValidPassword(edtPassword.text.toString())
            changeStatusForSignInButton()
        }
    }

    private fun handleEditTextPasswordListener() {
        edtPassword.addTextChangedListener {
            btnSignIn.isEnabled =
                isValidPassword(it.toString()) && isValidEmail(edtEmailId.text.toString())
            changeStatusForSignInButton()
        }
    }

    private fun handleButtonSignInListener() {
        btnSignIn.setOnClickListener {
            /*userLogin.email = edtEmailId.text.toString()
            userLogin.password = edtPassword.text.toString()*/
            if (isSignInEnabled()) {
                loginApp()
            } else {
                showInvalidLoginDialog()
            }
        }
    }

    private fun handleTextViewRegisterNowListener() {
        tvRegisterNow.setOnClickListener {
            openSignUpFragment()
        }
    }

    private fun changeStatusForSignInButton() {
        if (btnSignIn.isEnabled) {
            btnSignIn.setBackgroundResource(R.drawable.bg_button_enable)
        } else {
            btnSignIn.setBackgroundResource(R.drawable.bg_button_unable)
        }
    }

    private fun openSignUpFragment() {
        val transaction = fragmentManager?.beginTransaction()
        transaction?.add(R.id.fragmentContainer, SignUpFragment().apply {
            onRegisterSuccess = { userRegister ->
                userLogin = userRegister
                this@SignInFragment.edtEmailId.setText(userRegister.email)
                this@SignInFragment.edtPassword.setText(userRegister.password)
            }
        })
            ?.addToBackStack(null)
            ?.hide(this)
            ?.commit()
    }

    private fun loginApp() {
        val homeActivityIntent = Intent(this.context, HomeActivity::class.java)
        homeActivityIntent.putExtra(SignInActivityData.SIGN_IN_USER, userLogin)
        startActivity(homeActivityIntent)
        this.activity?.finish()
    }

    private fun showInvalidLoginDialog() {
        val builder = AlertDialog.Builder(this.context)
            .setTitle(getString(R.string.invalid_login_dialog_title))
            .setPositiveButton(getString(R.string.invalid_login_dialog_title_confirm)) { _: DialogInterface, _: Int -> }
        builder.show()
    }

    private fun isSignInEnabled(): Boolean =
        userLogin.email.equals(edtEmailId.text.toString()) && userLogin.password.equals(edtPassword.text.toString())
}
