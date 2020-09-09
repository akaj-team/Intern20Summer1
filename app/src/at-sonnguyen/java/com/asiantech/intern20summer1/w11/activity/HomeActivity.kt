package com.asiantech.intern20summer1.w11.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w11.fragment.HomeFragment

class HomeActivity : AppCompatActivity(){

    companion object{
        private const val BACK_TACK_KEY = "back-stack-key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w11_activity_home)
        replaceFragment(HomeFragment.newInstance())
    }

    internal fun replaceFragment(fragment : Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutHome,fragment)
        fragmentTransaction.addToBackStack(BACK_TACK_KEY)
        fragmentTransaction.commit()
    }
}
