package com.asiantech.intern20summer1.w4.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w4.fragment.SignInFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_w4)
        replaceFragment(SignInFragment())
    }

    /**
     * This function replace fragment on activity
     */
    private fun replaceFragment(fragment: Fragment, backStack: Boolean = false) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.llMain, fragment)
        if (backStack) fragmentTransaction.addToBackStack("")
        fragmentTransaction.commit()
    }

    /**
     * This function add fragment on activity
     */
    internal fun addFragment(fragment: Fragment, backStack: Boolean = false) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.llMain, fragment)
        if (backStack) fragmentTransaction.addToBackStack("")
        fragmentTransaction.commit()
    }
}
