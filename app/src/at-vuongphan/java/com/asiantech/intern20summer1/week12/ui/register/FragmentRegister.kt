package com.asiantech.intern20summer1.week12.ui.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.extension.hideKeyboard
import com.asiantech.intern20summer1.extension.isValidEmail
import com.asiantech.intern20summer1.extension.isValidPasswordW10
import com.asiantech.intern20summer1.week12.data.models.UserRegister
import com.asiantech.intern20summer1.week12.data.source.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.`at-vuongphan`.w10_fragment_register.*

class FragmentRegister : Fragment() {
    companion object {
        internal const val EMAIL_LENGTH = 264
        internal const val FULL_NAME_LENGTH = 64
        internal fun newInstance() = FragmentRegister()
    }

    internal var onRegisterSuccess: (email: String, password: String) -> Unit = { _, _ -> }
    private var isEmailValid = false
    private var isPassWordValid = false
    private var isUserNameValid = false
    private var viewModel: RegisterViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = RegisterViewModel(UserRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w10_fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListenerHideKeyboardLogin()
        initListener()
        addNewUser()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListenerHideKeyboardLogin() {
        scrViewRegister?.setOnTouchListener { view, _ ->
            view.clearFocus()
            view.hideKeyboard()
            true
        }
    }

    private fun addNewUser() {
        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()
        btnRegister?.setOnClickListener {
            viewModel?.addUserRegister(
                UserRegister(
                    edtEmail.text.toString(),
                    edtPassword.text.toString(),
                    edtUserName.text.toString()
                )
            )
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    if (it.isSuccessful) {
                        Toast.makeText(requireContext(), "Đăng kí thành công", Toast.LENGTH_SHORT)
                            .show()
                        onRegisterSuccess(email, password)
                        activity?.onBackPressed()
                    }
                }, {
                })
        }
    }

    private fun initListener() {
        initEmail()
        initPassword()
        initUserName()
    }

    private fun setBackgroundButton() {
        if (btnRegister.isEnabled) {
            btnRegister?.setBackgroundResource(R.drawable.bg_button_login)
        } else {
            btnRegister?.setBackgroundResource(R.drawable.bg_button_dis_enable)
        }
    }

    private fun initEmail() {
        edtEmail.addTextChangedListener {
            isEmailValid =
                edtEmail.text.toString()
                    .isValidEmail() && edtEmail.text.toString().length <= EMAIL_LENGTH
            btnRegister.isEnabled = isEmailValid && isPassWordValid && isUserNameValid
            setBackgroundButton()
        }
    }

    private fun initPassword() {
        edtPassword?.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                btnRegister?.isEnabled = false
            } else {
                isPassWordValid = edtPassword.text.toString().isValidPasswordW10()
                btnRegister?.isEnabled = isPassWordValid && isUserNameValid && isEmailValid
                setBackgroundButton()
            }
        }
    }

    private fun initUserName() {
        edtUserName?.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                btnRegister?.isEnabled = false
            } else {
                isUserNameValid = edtUserName.text.toString().length <= FULL_NAME_LENGTH
                btnRegister?.isEnabled = isEmailValid && isPassWordValid && isUserNameValid
                setBackgroundButton()
            }
        }
    }
}
