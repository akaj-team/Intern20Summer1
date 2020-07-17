package com.asiantech.intern20summer1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.fragment.SignInFragment
import com.asiantech.intern20summer1.fragment.SignUpFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = SignUpFragment()
        fragmentTransaction.add(R.id.llMain, fragment)
        fragmentTransaction.commit()
    }
}
