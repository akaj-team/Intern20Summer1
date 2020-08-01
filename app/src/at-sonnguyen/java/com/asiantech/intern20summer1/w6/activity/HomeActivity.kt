package com.asiantech.intern20summer1.w6.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w6.adapter.HomeFragmentAdapter
import kotlinx.android.synthetic.`at-sonnguyen`.w6_activity_home.*

class HomeActivity : AppCompatActivity() {

    private val adapter = HomeFragmentAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w6_activity_home)
        viewPagerHome.adapter = adapter
        tabHome.setupWithViewPager(viewPagerHome)
    }
}
