package com.asiantech.intern20summer1.w6.secondactivity

import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.`at-huybui`.activity_view_pager_one.*
import kotlinx.android.synthetic.`at-huybui`.activity_view_pager_two.*

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 07/30/20
 * This is class for second activity
 */

class ViewPagerTwoActivity : AppCompatActivity() {

    private val viewPagerAdapter by lazy { ViewPagerTwoAdapter(supportFragmentManager) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_two)
        initView()
    }

    private fun initView() {
        viewpagerTwo?.apply {
            adapter = viewPagerAdapter
        }
        tabLayout?.apply {
            setupWithViewPager(viewpagerTwo)
            getTabAt(0)?.text = context.getString(R.string.w6_tab_layout_tab0_text)
            getTabAt(1)?.text = context.getString(R.string.w6_tab_layout_tab1_text)
            getTabAt(2)?.text = context.getString(R.string.w6_tab_layout_tab2_text)
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                    d("XXXX","reselect tab ${tab?.position}")
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    d("XXXX","unselect tab ${tab?.position}")
                }
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        handleSelectItem(it.position)
                    }
                    d("XXXX","select tab ${tab?.position}")
                }
            })
        }
    }

    private fun handleSelectItem(position: Int, smoothScroll: Boolean = true) {
        viewpagerTwo.setCurrentItem(position, smoothScroll)
    }
}
