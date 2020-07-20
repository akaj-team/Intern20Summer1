package com.asiantech.intern20summer1.w4.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w4.fragment.SignInFragmentW4

class MainActivityW4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = SignInFragmentW4()
        fragmentTransaction.add(R.id.llMain, fragment)
        fragmentTransaction.commit()
    }
}
