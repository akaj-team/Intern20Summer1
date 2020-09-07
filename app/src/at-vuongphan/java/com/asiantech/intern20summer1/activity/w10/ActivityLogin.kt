package com.asiantech.intern20summer1.activity.w10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.fragment.w10.FragmentLogin

class ActivityLogin : AppCompatActivity() {
    companion object {
        internal const val LENGTH_MAX = 16
        internal const val LENGTH_MIN = 8
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w10_activity_login)
        openFragment(FragmentLogin.newInstance())
    }

    internal fun openFragment(
        fragment: Fragment,
        backStack: Boolean = false,
        nameBackStack: String = "null"
    ) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frContainer, fragment)
        if (backStack) {
            fragmentTransaction.addToBackStack(nameBackStack)
        }
        fragmentTransaction.commit()
    }
}
