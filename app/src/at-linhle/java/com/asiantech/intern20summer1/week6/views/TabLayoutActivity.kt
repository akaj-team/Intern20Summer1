package com.asiantech.intern20summer1.week6.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week6.adapters.TabLayoutAdapter
import kotlinx.android.synthetic.`at-linhle`.activity_tab_layout.*

class TabLayoutActivity : AppCompatActivity() {
    private val tabLayoutAdapter by lazy {
        TabLayoutAdapter(
            supportFragmentManager
        )
    }

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

            getTabAt(0)?.text = getString(R.string.tab_layout_activity_home_text_view)
            getTabAt(1)?.text = getString(R.string.tab_layout_activity_info_text_view)
            getTabAt(2)?.text = getString(R.string.tab_layout_activity_another_text_view)
        }
    }
}
