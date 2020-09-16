package com.asiantech.intern20summer1.week12.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week12.fragments.LoginFragment.Companion.KEY_STRING_FULL_NAME
import com.asiantech.intern20summer1.week12.fragments.LoginFragment.Companion.SHARED_PREFERENCE_TOKEN
import kotlinx.android.synthetic.`at-linhle`.activity_post_home.*

class HomeRxActivity : AppCompatActivity() {

    private var fullName: String? = null
    private var token: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_home)
        getData()
        toolbarHome?.title = fullName
    }

    private fun getData() {
        fullName = intent?.getStringExtra(KEY_STRING_FULL_NAME)
        token = intent?.getStringExtra(SHARED_PREFERENCE_TOKEN)
    }
}
