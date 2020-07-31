package com.asiantech.intern20summer1.week6.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week6.adapter.CustomPagerAdapter
import com.zhpan.indicator.DrawableIndicator

class FirstPageActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)
        configStatusBar()

        viewPager = findViewById(R.id.viewpager)
        viewPager.adapter = CustomPagerAdapter(this)

        val indicatorView = findViewById<DrawableIndicator>(R.id.indicator_view)
        val sizeIndicator = resources.getDimensionPixelOffset(R.dimen.size_indicator)
        viewPager.let {
            indicatorView
                .setIndicatorGap(resources.getDimensionPixelOffset(R.dimen.space_size_indicator))
                .setIndicatorDrawable(R.drawable.ic_heart, R.drawable.ic_heart_filled)
                .setIndicatorSize(sizeIndicator, sizeIndicator, sizeIndicator, sizeIndicator)
                .setupWithViewPager(it)
        }
    }

    private fun configStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}
