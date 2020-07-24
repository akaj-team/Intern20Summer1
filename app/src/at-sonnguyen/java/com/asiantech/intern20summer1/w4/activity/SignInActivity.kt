package com.asiantech.intern20summer1.w4.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w4.fragment.LogInFragment

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        window.decorView.setBackgroundColor(Color.WHITE)
        addSignUpFragment()
    }

    private fun addSignUpFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val fragment = LogInFragment()
        fragmentTransaction.replace(
            R.id.llFragment,
            fragment,
            resources.getString(R.string.add_to_back_stack_key)
        )
        fragmentTransaction.commit()
    }
}
