package com.asiantech.intern20summer1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.W4SignUpFragment.Companion.onRegisterSuccess
import kotlinx.android.synthetic.`at-hoangtran`.w4_login_fragment.*
import kotlinx.android.synthetic.`at-hoangtran`.w4_sign_up_fragment.*

class W4LoginFragment : Fragment() {

    private var user = User("", "", "", "", "")
    var emailCheck = false
    var passCheck = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w4_login_fragment, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleSignUp()
        handleEditText(edt_login_email)
        handleEditText(edt_login_password)
        handleLogin()
    }

    private fun handleSignUp() {
        tv_signUp.setOnClickListener {
            val trans = fragmentManager?.beginTransaction()
            trans?.add(R.id.fl_container, W4SignUpFragment().apply {
                onRegisterSuccess = { user ->
                    this@W4LoginFragment.user = user
                    this@W4LoginFragment.edt_login_email.setText(user.email)
                    this@W4LoginFragment.edt_login_password.setText(user.pass)
                }
            })
                ?.addToBackStack(null)
                ?.hide(this)
                ?.commit()
        }
    }

    private fun handleLogin() {
        btn_login.setOnClickListener {
            val intent = Intent(activity, W4HomeActivity::class.java)
            intent.putExtra("user", user)
            activity?.startActivity(intent)
            activity?.finish()
        }
    }

    private fun handleEditText(edt: EditText) {
        edt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val str = s.toString()
                when (edt) {
                    edt_login_email -> {
                        emailCheck = str == user.email
                    }
                    edt_password -> {
                        passCheck = str == user.pass
                    }
                }
                if (emailCheck) {
                    btn_login.isEnabled = true
                }
            }
        })
    }
}
