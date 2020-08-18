package com.asiantech.intern20summer1.activity.w6

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.adapter.w6.PagerAdapterFragment
import com.asiantech.intern20summer1.fragment.w6.PagePosition
import kotlinx.android.synthetic.`at-vuongphan`.activity_viewpager.*

class ViewPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)
        supportActionBar?.hide()
        initViews()
        addChangeListener()
        initTextViewNextListener()
    }

    private fun initViews() {
        viewPager?.apply {
            adapter =
                PagerAdapterFragment(
                    supportFragmentManager
                )
            dotsIndicator?.setViewPager(this)
        }
    }

    private fun initTextViewNextListener() {
        tvNext?.setOnClickListener {
            viewPager?.setCurrentItem(viewPager.currentItem + 1, true)
            if (viewPager?.currentItem == PagePosition.THREE.number) {
                Handler().postDelayed({
                    startActivity(Intent(this, ViewPagerActivityTwo::class.java))
                }, 500)
            }
        }
    }

    private fun addChangeListener() {
        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                tvNext?.text =
                    if (position < 2) getString(R.string.text_view_next_text) else getString(R.string.text_view_skip_text)
            }
        })
    }
}
