package com.asiantech.intern20summer1.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.adapter.PagerAdapterFragment
import com.asiantech.intern20summer1.data.list
import kotlinx.android.synthetic.main.activity_viewpager.*

class ViewPagerActivity : AppCompatActivity() {
    var pos: Int=0
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
            adapter=PagerAdapterFragment(supportFragmentManager)
            dotsIndicator.setViewPager(this)
        }
    }

    private fun initTextViewNextListener() {
        tvNext.setOnClickListener {
            viewPager.setCurrentItem(viewPager.currentItem + 1, true)
            if (pos == list.size - 1) {
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
                if (position < 2) {
                    tvNext.text=getString(R.string.text_view_next_text)
                } else {
                    tvNext.text=getString(R.string.text_view_skip_text)
                }
                pos=position
            }
        })
    }
}
