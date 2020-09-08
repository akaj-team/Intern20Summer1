package com.asiantech.intern20summer1.week10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.activity_home.*

class HomeActivity : AppCompatActivity() {

    private var token: String? = null
    private var name: String? = null
    private var userId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        handleNewPostButton()
        replaceFragment(HomeFragment.newInstance(name, token, userId))
    }

    internal fun replaceFragment(
        fragment: Fragment,
        backStack: Boolean = false,
        nameBackStack: String = "null"
    ) {
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(
            R.id.flToolbarContainer, fragment
        )
        if (backStack) {
            trans.addToBackStack(nameBackStack)
        }
        trans.commit()
    }

    private fun handleNewPostButton() {
        btnNewPost.setOnClickListener {
            replaceFragment(NewPostFragment.newInstance(token), true)
        }
    }
}
