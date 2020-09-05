package com.asiantech.intern20summer1.activity.w6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.adapter.w6.PagerAdapterActivityTwo
import kotlinx.android.synthetic.`at-vuongphan`.activity_view_pager_two.*

class ViewPagerActivityTwo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_two)
        tabLayout?.setupWithViewPager(viewPager2)
        initViews()
    }

    private fun initViews() {
        viewPager2?.apply {
            adapter =
                PagerAdapterActivityTwo(supportFragmentManager)
            tabLayout?.apply {
                getTabAt(0)?.text = context.getString(R.string.tab_layout_description_one)
                getTabAt(1)?.text = context.getString(R.string.tab_layout_description_two)
                getTabAt(2)?.text = context.getText(R.string.tab_layout_description_three)
            }
        }
    }
}
