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
        initView()
    }

    private fun initView() {
        viewPagerHome.adapter = adapter
        tabHome.apply {
            setupWithViewPager(viewPagerHome)
            getTabAt(0)?.text = getString(R.string.w6_tab1_name)
            getTabAt(1)?.text = getString(R.string.w6_tab2_name)
            getTabAt(2)?.text = getString(R.string.w6_tab3_name)
        }
    }
}
