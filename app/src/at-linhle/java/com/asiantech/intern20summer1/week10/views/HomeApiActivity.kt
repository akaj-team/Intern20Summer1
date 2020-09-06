package com.asiantech.intern20summer1.week10.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week10.fragments.HomeFragment
import com.asiantech.intern20summer1.week10.fragments.LoginFragment.Companion.KEY_STRING_FULL_NAME
import com.asiantech.intern20summer1.week10.fragments.LoginFragment.Companion.KEY_STRING_USER_ID
import com.asiantech.intern20summer1.week10.fragments.LoginFragment.Companion.SHARED_PREFERENCE_TOKEN

class HomeApiActivity : AppCompatActivity() {

    private var fullName: String? = null
    private var token: String? = null
    private var userId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api_home)
        getData()
        replaceFragment(HomeFragment.newInstance(fullName, token, userId))
    }

    internal fun replaceFragment(
        fragment: Fragment,
        backStack: Boolean = false,
        nameBackStack: String = "null"
    ) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flApiHomeActivity, fragment)
        if (backStack) {
            fragmentTransaction.addToBackStack(nameBackStack)
        }
        fragmentTransaction.commit()
    }

    private fun getData() {
        intent?.apply {
            fullName = getStringExtra(KEY_STRING_FULL_NAME)
            token = getStringExtra(SHARED_PREFERENCE_TOKEN)
            userId = getIntExtra(KEY_STRING_USER_ID, 0)
        }
    }
}
