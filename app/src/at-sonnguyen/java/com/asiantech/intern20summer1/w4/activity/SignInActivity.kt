package com.asiantech.intern20summer1.w4.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w4.fragment.LogInFragment

class SignInActivity : AppCompatActivity() {
    companion object {
        private const val ADD_TO_BACK_STACK_KEY_NAME = "add to back Stack"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        addSignUpFragment()
    }

    private fun addSignUpFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val fragment = LogInFragment()
        fragmentTransaction.replace(R.id.llFragment, fragment, ADD_TO_BACK_STACK_KEY_NAME)
        fragmentTransaction.commit()
    }
}
