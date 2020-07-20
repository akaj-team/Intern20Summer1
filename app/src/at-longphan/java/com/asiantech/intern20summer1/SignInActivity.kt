package com.asiantech.intern20summer1

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {
    companion object {

    }
    private val fragmentManager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        // Set black text with light status bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        addSignInFragment()
    }

    private fun addSignInFragment() {
        //fragmentManager.popBackStack("ROOT_TAG", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val transaction = fragmentManager.beginTransaction()
        val signInFragment = SignInFragment()
        transaction.replace(R.id.fragmentContainer, signInFragment)
        transaction.commit()
    }
}
