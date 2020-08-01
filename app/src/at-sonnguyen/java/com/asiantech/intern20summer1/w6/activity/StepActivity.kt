package com.asiantech.intern20summer1.w6.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w6.adapter.StepFragmentAdapter
import kotlinx.android.synthetic.`at-sonnguyen`.w6_activity_step.*

class StepActivity : AppCompatActivity() {
    private var currentPosition = 0
    private val adapter = StepFragmentAdapter(supportFragmentManager)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w6_activity_step)
        initAdapter()
        initListener()
    }

    private fun initAdapter() {
        viewPagerStep.adapter = adapter
        circleIndicatorStep.setViewPager(viewPagerStep)
        viewPagerStep.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                if (position < 2) {
                    tvNext.text = resources.getString(R.string.w6_next_text_view_next)
                } else {
                    tvNext.text = resources.getString(R.string.w6_next_text_view_skip)
                }
                currentPosition = position
            }
        })
    }

    private fun initListener() {
        tvNext.setOnClickListener {
            if (currentPosition < 2) {
                viewPagerStep.currentItem = currentPosition + 1
            } else {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }
}