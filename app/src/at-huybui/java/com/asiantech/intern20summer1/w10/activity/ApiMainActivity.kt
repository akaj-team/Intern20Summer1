package com.asiantech.intern20summer1.w10.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.fragment.SplashFragment

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 02/09/2020.
 * This is ApiMainActivity class. It is main activity for application
 */

class ApiMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w10_activity_api)
        setColorStatusBar()
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

    private fun setColorStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.apply {
                window.statusBarColor = ContextCompat.getColor(this, R.color.background_white)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    window.setDecorFitsSystemWindows(false)
                } else {
                    @Suppress("DEPRECATION")
                    window.decorView.systemUiVisibility =
                        window.decorView.systemUiVisibility.or(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                }
            }
        }
    }

    internal fun showToast(context: Context, any: Any, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, any.toString(), duration).show()
    }
}
