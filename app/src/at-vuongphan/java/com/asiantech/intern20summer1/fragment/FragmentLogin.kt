package com.asiantech.intern20summer1.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.HomeActivity
import com.asiantech.intern20summer1.activity.SignInActivity
import com.asiantech.intern20summer1.data.User
import com.asiantech.intern20summer1.extension.hideKeyboard
import com.asiantech.intern20summer1.extension.isValidEmail
import com.asiantech.intern20summer1.extension.isValidPasswordW4
import com.asiantech.intern20summer1.extension.textChangedListener
import kotlinx.android.synthetic.`at-vuongphan`.fragment_sign_in.*

class FragmentLogin : Fragment() {
    private var nameLogin: String? = null
    private var emailLogin: String? = null
    private var passwordLogin: String? = null
    private var phoneLogin: String? = null

    companion object {
        internal const val TITLE_DIALOG = "Title Sign In"
        internal const val MESSAGE = "Fail username or password"
        internal const val TEXT_EXIT = "Exit"
        internal const val KEY_DATA_REGISTER = "data"
        internal const val KEY_DATA_LOGIN = "login"
        internal fun newInstance(data: User): FragmentLogin {
            return FragmentLogin().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_DATA_REGISTER, data)
                }
            }
        }

        internal fun newInstance(): FragmentLogin {
            return FragmentLogin().apply {}
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        getDataFromRegister()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListenerHideKeyboardLogin() {
        llFragmentLogin?.setOnTouchListener { view, _ ->
            view.clearFocus()
            view.hideKeyboard()
            true
        }
    }

    private fun initListener() {
        openSignUpFragment()
        initEnableButtonLogin()
        initLoginButton()
        initListenerHideKeyboardLogin()
    }

    private fun initLoginButton() {
        btnLogin.setOnClickListener {
            if (edtEmail.text.toString() == emailLogin && edtPassword.text.toString() == passwordLogin) {
                val intent = Intent(activity, HomeActivity::class.java)
                val bundle = Bundle()
                val data = nameLogin?.let { it1 ->
                    emailLogin?.let { it2 ->
                        phoneLogin?.let { it3 ->
                            passwordLogin?.let { it4 ->
                                User(
                                    it1,
                                    it2,
                                    it3,
                                    it4
                                )
                            }
                        }
                    }
                }
                bundle.putParcelable(KEY_DATA_LOGIN, data)
                intent.putExtras(bundle)
                startActivity(intent)
                activity?.finish()
            } else {
                initDialogLoginError()
            }
        }
    }

    private fun getDataFromRegister() {
        arguments?.getParcelable<User>(KEY_DATA_REGISTER)?.let {
            nameLogin = it.name
            emailLogin = it.email
            phoneLogin = it.phoneNumber
            passwordLogin = it.password
            edtEmail.setText(it.email)
            edtPassword.setText(it.password)
        }
    }

    private fun openSignUpFragment() {
        tvRegisterNow.setOnClickListener {
            (activity as? SignInActivity)?.openSignUp()
        }
    }

    private fun isCorrectFormat() =
        edtEmail.text.toString().isValidEmail() && edtPassword.text.toString().isValidPasswordW4()

    private fun initEnableButtonLogin() {
        edtEmail.textChangedListener(onTextChanged = { _: CharSequence?, _, _, _ ->
            btnLogin.isEnabled = isCorrectFormat()
        })
        edtPassword.textChangedListener(onTextChanged = { _: CharSequence?, _, _, _ ->
            btnLogin.isEnabled = isCorrectFormat()
        })
    }

    private fun initDialogLoginError() {
        AlertDialog.Builder(activity).apply {
            setTitle(TITLE_DIALOG)
            setMessage(MESSAGE)
            setPositiveButton(TEXT_EXIT) { _: DialogInterface, _: Int -> }
            show()
        }
    }
}
