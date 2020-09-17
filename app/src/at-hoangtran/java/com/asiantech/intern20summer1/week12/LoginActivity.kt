package com.asiantech.intern20summer1.week12

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week12.LoginFragment.Companion.TOKEN_SHARE_REFERENCE
import com.asiantech.intern20summer1.week12.LoginFragment.Companion.USER_SHARE_PREFERENCE
import kotlinx.android.synthetic.`at-hoangtran`.fragment_login.*

class LoginActivity : AppCompatActivity() {

    private var token: String? = null

    @SuppressLint("ShowToast", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        handleAutoLogin()
        showLoginFragment()
    }

    private fun showLoginFragment() {
        val trans = supportFragmentManager.beginTransaction()
        val fragment = LoginFragment()
        trans.replace(R.id.fl_container, fragment).commit()
    }

    internal fun showSignUpFragment() {
        tv_signUp.setOnClickListener {
            val trans = supportFragmentManager.beginTransaction()
            trans.replace(
                R.id.fl_container, SignUpFragment().apply {
                    onRegisterSuccess = { email, pass ->
                        LoginFragment().edt_login_email.setText(email)
                        LoginFragment().edt_login_password.setText(pass)
                    }
                }
            )
                .addToBackStack(null)
                .hide(LoginFragment())
                .commit()
        }
    }

    private fun checkSharePreferences(): Boolean {
        val shareRef = getSharedPreferences(USER_SHARE_PREFERENCE, Context.MODE_PRIVATE)
        if (shareRef?.getString(TOKEN_SHARE_REFERENCE, null) == null) {
            return true
        }
        token = shareRef.getString(TOKEN_SHARE_REFERENCE, token)
        return false
    }

//    private fun handleAutoLogin() {
//        if (!checkSharePreferences()) {
//            val callApi = token?.let {
//                ApiClient.createUserService()?.handleAutoSignIn(it)
//            }
//            callApi?.enqueue(object : retrofit2.Callback<User> {
//                override fun onFailure(call: Call<User>, t: Throwable) {
//                    Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onResponse(call: Call<User>, response: Response<User>) {
//                    if (response.isSuccessful) {
//                        response.body()?.apply {
//                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
//                            intent.putExtra(KEY_FULL_NAME, this.fullName)
//                            intent.putExtra(TOKEN_SHARE_REFERENCE, token)
//                            intent.putExtra(KEY_USER_ID, id)
//                            startActivity(intent)
//                            finish()
//                        }
//                    }
//                }
//            })
//        } else {
//            showLoginFragment()
//        }
//    }
}
