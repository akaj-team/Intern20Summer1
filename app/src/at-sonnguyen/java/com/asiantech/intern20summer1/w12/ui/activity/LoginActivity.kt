package com.asiantech.intern20summer1.w12.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w12.data.source.repository.LoginRepository
import com.asiantech.intern20summer1.w12.ui.login.LoginFragment
import com.asiantech.intern20summer1.w12.ui.login.LoginVMContact
import com.asiantech.intern20summer1.w12.ui.login.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private var viewModel: LoginVMContact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w12_activity_login)
        viewModel = LoginViewModel(LoginRepository())
        replaceFragment(LoginFragment.newInstance("", ""))
    }

    internal fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutMain, fragment, null)
            .addToBackStack(null)
            .commit()
    }
}
