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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.fragment_sign_up.*
import retrofit2.Call
import retrofit2.Response

@Suppress("DEPRECATION")
class SignUpFragment : Fragment() {
    companion object {
        private const val RESPONSE_CODE = 400
        internal fun newInstance() = SignUpFragment()
    }

    internal var onRegisterSuccess: (email: String, pass: String) -> Unit = { _, _ -> }
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
            val name = edt_name.text.toString()
            val email = edt_email.text.toString()
            val pass = edt_password.text.toString()
            val callApi = ApiClient.createUserService()?.addNewUser(UserRegister(email, pass, name))
            callApi?.enqueue(object : retrofit2.Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        Toast.makeText(activity, "Register success", Toast.LENGTH_SHORT).show()
                        onRegisterSuccess(email, pass)
                        activity?.onBackPressed()
                    } else {
                        if (response.code() == RESPONSE_CODE) {
                            Toast.makeText(activity, "Email already registered", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(activity, "Fail to register", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            })
        }
    }

    private fun handleBtnBack() {
        img_back.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}
