package com.asiantech.intern20summer1.week10.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week10.api.ApiClient
import com.asiantech.intern20summer1.week10.fragments.LoginFragment
import com.asiantech.intern20summer1.week10.fragments.LoginFragment.Companion.KEY_STRING_FULL_NAME
import com.asiantech.intern20summer1.week10.fragments.LoginFragment.Companion.SHARED_PREFERENCE_FILE
import com.asiantech.intern20summer1.week10.fragments.LoginFragment.Companion.SHARED_PREFERENCE_TOKEN
import com.asiantech.intern20summer1.week10.models.User
import retrofit2.Call
import retrofit2.Response

class LoginMainActivity : AppCompatActivity() {

    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_main)
        handleAutoLogin()
    }

    internal fun replaceFragment(
        fragment: Fragment,
        backStack: Boolean = false,
        nameBackStack: String = "null"
    ) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flLoginContainer, fragment)
        if (backStack) {
            fragmentTransaction.addToBackStack(nameBackStack)
        }
        fragmentTransaction.commit()
    }

    private fun checkFileSharedPreferences(): Boolean {
        val sharedRef = getSharedPreferences(SHARED_PREFERENCE_FILE, MODE_PRIVATE)
        if (sharedRef?.getString(SHARED_PREFERENCE_TOKEN, null) == null) {
            return true
        }
        token = sharedRef.getString(SHARED_PREFERENCE_TOKEN, token)
        return false
    }

    private fun handleAutoLogin() {
        if (!checkFileSharedPreferences()) {
            val callApi = token?.let { ApiClient.createUserService()?.handleAutoSignIn(it) }
            callApi?.enqueue(object : retrofit2.Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@LoginMainActivity, t.message, Toast.LENGTH_SHORT).show()
                    replaceFragment(LoginFragment.newInstance())
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        response.body()?.apply {
                            val intent = Intent(this@LoginMainActivity, HomeApiActivity::class.java)
                            intent.putExtra(KEY_STRING_FULL_NAME, this.fullName)
                            intent.putExtra(SHARED_PREFERENCE_TOKEN, token)
                            startActivity(intent)
                            finish()
                        }
                    }
                }

            })
        } else {
            replaceFragment(LoginFragment.newInstance())
        }
    }
}
