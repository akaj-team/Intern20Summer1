package com.asiantech.intern20summer1.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.extension.replaceFragment
import com.asiantech.intern20summer1.fragment.FragmentLogin

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        openFragment(FragmentLogin.newInstance())
    }

    private fun openFragment(fragment: Fragment) {
        replaceFragment(R.id.frContainer, fragment, false)
    }
}
