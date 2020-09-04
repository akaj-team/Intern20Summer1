package com.asiantech.intern20summer1.w10.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.fragment.PostDialogFragment
import com.asiantech.intern20summer1.w10.fragment.SplashFragment

class ApiMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w10_activity_ipi)
        replaceFragment(SplashFragment.newInstance())
    }

    internal fun replaceFragment(fragment: Fragment, backStack: Boolean = false) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerMain, fragment)
        if (backStack) fragmentTransaction.addToBackStack("")
        fragmentTransaction.commit()
    }

    internal fun addFragment(fragment: Fragment, backStack: Boolean = false) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.containerMain, fragment)
        if (backStack) fragmentTransaction.addToBackStack("")
        fragmentTransaction.commit()
    }

    internal fun handleShowDialogFragment() {
        val fragmentManager = supportFragmentManager
        val fragment = PostDialogFragment.newInstance()
        fragment.show(fragmentManager, null)
    }

    internal fun showToast(context: Context, any: Any, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, any.toString(), duration).show()
    }
}
