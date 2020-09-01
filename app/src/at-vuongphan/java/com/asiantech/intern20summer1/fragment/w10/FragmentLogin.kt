package com.asiantech.intern20summer1.fragment.w10

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.w10.ActivityLogin
import com.asiantech.intern20summer1.activity.w10.RecyclerViewNewFeed
import com.asiantech.intern20summer1.extension.hideKeyboard
import com.asiantech.intern20summer1.extension.isValidEmail
import com.asiantech.intern20summer1.extension.isValidPasswordW10
import kotlinx.android.synthetic.`at-vuongphan`.w10_fragment_login.*

class FragmentLogin : Fragment() {
    private var isUserNameValid = false
    private var isPassWordValid = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w10_fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initEmail()
        initPassword()
    }

    private fun initListener() {
        initTextViewRegister()
        initButtonLogin()
        initListenerHideKeyboardLogin()
    }

    private fun initTextViewRegister() {
        tvCreateNewAccount?.setOnClickListener {
            (activity as? ActivityLogin)?.openFragment(FragmentRegister(), true)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListenerHideKeyboardLogin() {
        scrViewLogin?.setOnTouchListener { view, _ ->
            view.clearFocus()
            view.hideKeyboard()
            true
        }
    }

    private fun initButtonLogin() {
        btnLogin?.setOnClickListener {
            startActivity(Intent(context, RecyclerViewNewFeed::class.java))
        }
    }

    private fun initEmail() {
        edtEmail.addTextChangedListener {
            isUserNameValid = edtEmail.text.toString().isValidEmail()
            btnLogin.isEnabled = isUserNameValid && isPassWordValid
        }
    }

    private fun initPassword() {
        edtPassword?.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                btnLogin?.isEnabled = false
            } else {
                isPassWordValid = edtPassword.text.toString().isValidPasswordW10()
                btnLogin?.isEnabled = isPassWordValid && isUserNameValid
            }
        }
    }
}
