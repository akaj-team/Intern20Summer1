package com.asiantech.intern20summer1.w6.firstactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-huybui`.activity_view_pager_one.*


/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 07/30/20
 * This is class for first activity
 */

class ViewPagerOneActivity : AppCompatActivity() {

    private val viewPagerAdapter by lazy { ViewPagerOneAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_one)
        initView()
    }

    private fun initView() {
        viewpagerOne?.apply {
            adapter = viewPagerAdapter
        }
    }

    internal fun handleNextFragment(smoothScroll: Boolean = true) {
        viewpagerOne.setCurrentItem(viewpagerOne.currentItem + 1, smoothScroll)
    }
}
