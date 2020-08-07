package com.asiantech.intern20summer1.week6.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week6.adapter.SecondViewPagerAdapter
import kotlinx.android.synthetic.`at-phuongle`.activity_second.*

class SecondActivity : AppCompatActivity() {
    companion object {
        const val VIEWPAGER_PAGE_MARGIN = 80
        const val TITLE_HOME = "HOME"
        const val TITLE_INFO = "INFO"
        const val TITLE_ANOTHER = "ANOTHER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        initViewPager()
    }

    private fun initViewPager() {
        viewPagerSecond?.apply {
            adapter = SecondViewPagerAdapter(supportFragmentManager)
            pageMargin = VIEWPAGER_PAGE_MARGIN
            tabLayout.setupWithViewPager(viewPagerSecond)
            tabLayout.getTabAt(0)?.text = TITLE_HOME
            tabLayout.getTabAt(1)?.text = TITLE_INFO
            tabLayout.getTabAt(2)?.text = TITLE_ANOTHER
        }
    }
}