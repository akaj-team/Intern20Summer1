package com.asiantech.intern20summer1.week10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        handleNewPostButton()
    }

    private fun handleNewPostButton() {
        btnNewPost.setOnClickListener {
            val trans = supportFragmentManager.beginTransaction()
            trans.add(
                R.id.flToolbarContainer, NewPostFragment()
            )
                .commit()
        }
    }
}
