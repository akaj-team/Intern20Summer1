package com.asiantech.intern20summer1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.asiantech.intern20summer1.model.User
import kotlinx.android.synthetic.`at-longphan`.fragment_sign_in.*

class SignInFragment : BaseFragment() {

    companion object {
        private val PASSWORD_REGEX = "^(?=.*[0-9]).{8,16}\$".toRegex()
        val userList =
            mutableListOf(User("longphan@gmail.com", "Long Phan", "0835850444", "long1998"))
        // ADD SAMPLE DATA
        var userLogin = User("", "")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleListener()


    }

    private fun handleListener() {
        tvRegisterNow.setOnClickListener {
            openSignUpFragment()
        }

        edtEmailId.addTextChangedListener {
            btnSignIn.isEnabled =
                isValidEmail(it.toString()) && isValidPassword(edtPassword.text.toString())
        }

        edtPassword.addTextChangedListener {
            btnSignIn.isEnabled =
                isValidPassword(it.toString()) && isValidEmail(edtEmailId.text.toString())
        }

        btnSignIn.setOnClickListener {
            var email = edtEmailId.text.toString()
            var password = edtPassword.text.toString()

            userLogin.email = email
            userLogin.password = password

            if (checkSignIn(userLogin)) {
                startActivity(Intent(context, HomeActivity::class.java))
                this.activity?.finish()
            } else Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openSignUpFragment() {
        val transaction = fragmentManager?.beginTransaction()
        val signUpFragment = SignUpFragment()
        transaction?.add(R.id.fragmentContainer, signUpFragment)
        transaction?.addToBackStack(null)
        transaction?.hide(this)
        transaction?.commit()
    }

    private fun isValidEmail(email: String) =
        androidx.core.util.PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String) = password.matches(PASSWORD_REGEX)

    private fun checkSignIn(user: User) =
        (userList.filter { it.email.equals(user.email) && it.password.equals(user.password) }.size == 1)

}
