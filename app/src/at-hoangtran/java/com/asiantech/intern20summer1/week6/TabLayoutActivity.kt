package com.asiantech.intern20summer1.week6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.activity_tab_layout.*

class TabLayoutActivity : AppCompatActivity() {
    private val tabLayoutAdapter = TabLayoutAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout)

        initView()
    }

    private fun initView() {
        viewPager?.apply {
            adapter = tabLayoutAdapter
        }
        tabLayout?.apply {
            setupWithViewPager(viewPager)

            getTabAt(0)?.text = getString(R.string.tab_layout_text_view_home)
            getTabAt(1)?.text = getString(R.string.tab_layout_text_view_info)
            getTabAt(2)?.text = getString(R.string.tab_layout_text_view_another)
        }
    }
}
