package com.asiantech.intern20summer1.week12

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week12.LoginFragment.Companion.USER_SHARE_PREFERENCE

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.flToolbarContainer, fragment).commit()
    }

    internal fun getData(): String? {
        val sharedPreferences = getSharedPreferences(USER_SHARE_PREFERENCE, Context.MODE_PRIVATE)
        return sharedPreferences.getString(LoginFragment.TOKEN_SHARE_REFERENCE, "aaa")
    }
}
