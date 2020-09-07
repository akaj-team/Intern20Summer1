package com.asiantech.intern20summer1.activity.w10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.fragment.w10.NewFeedFragment
import com.asiantech.intern20summer1.model.w10.UserAutoSignIn

class RecyclerViewNewFeed : AppCompatActivity() {
    internal lateinit var token: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w10_activity_recyclerview)
        getData()
        val fragment = NewFeedFragment.newInstance()
        val bundle = Bundle()
        bundle.putString(resources.getString(R.string.key_data), token)
        fragment.arguments = bundle
        openFragment(fragment)
    }

    internal fun openFragment(
        fragment: Fragment,
        backStack: Boolean = false,
        nameBackStack: String = "null"
    ) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flMainActivity, fragment)
        if (backStack) {
            fragmentTransaction.addToBackStack(nameBackStack)
        }
        fragmentTransaction.commit()
    }

    private fun getData() {
        (intent?.extras?.getSerializable(resources.getString(R.string.key_data)) as? UserAutoSignIn).let {
            token = it?.token.toString()
        }
    }
}
