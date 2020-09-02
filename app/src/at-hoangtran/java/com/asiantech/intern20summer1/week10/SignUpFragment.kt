package com.asiantech.intern20summer1.week10

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.fragment_sign_up.*

@Suppress("DEPRECATION")
class SignUpFragment : Fragment() {
    companion object {
        internal var onRegisterSuccess: (user: User) -> Unit = {}
    }

    var emailCheck = false
    var passCheck = false
    var confirmPassCheck = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEditText(edt_email)
        handleEditText(edt_password)
        handleEditText(edt_confirm_password)
        handleBtnRegister()
        handleBtnBack()
        hideKeyBoard()
    }

    private val user = User("", "", "", "")

    @SuppressLint("ClickableViewAccessibility")
    private fun hideKeyBoard() {
        ln_main_sign_up.setOnTouchListener { it, _ ->
            it.requestFocus()
            it.hideSoftKeyboard()
            true
        }
    }

    private fun isValidPassword(str: String): Boolean {
        val regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$".toRegex()
        return str.matches(regex)
    }

    private fun isValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidConfirmPassword(pass: String) =
        (pass == edt_password.text.toString() && isValidPassword(pass))

    private fun handleEditText(edt: EditText) {
        edt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val str = s.toString()
                when (edt) {
                    edt_email -> {
                        emailCheck = isValidEmail(str)
                    }
                    edt_password -> {
                        confirmPassCheck = isValidPassword(str)

                    }
                    edt_confirm_password -> {
                        passCheck = isValidConfirmPassword(str)
                    }
                }
                btn_register.isEnabled = emailCheck && passCheck && confirmPassCheck
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun handleBtnRegister() {
        btn_register.setOnClickListener {
            user.email = edt_email.text.toString()
            user.pass = edt_password.text.toString()
            user.name = edt_name.text.toString()
            onRegisterSuccess(
                user
            )
            fragmentManager?.popBackStack()
        }
    }

    private fun handleBtnBack() {
        img_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}
