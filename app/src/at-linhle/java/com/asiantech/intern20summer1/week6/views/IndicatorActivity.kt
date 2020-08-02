package com.asiantech.intern20summer1.week6.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week6.adapters.IndicatorAdapter
import com.asiantech.intern20summer1.week6.extensions.addOnPageChangeListener
import kotlinx.android.synthetic.`at-linhle`.activity_indicator.*

class IndicatorActivity : AppCompatActivity() {
    private val viewPagerAdapter by lazy {
        IndicatorAdapter(
            supportFragmentManager
        )
    }
    private var pagePosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indicator)
        initView()
        handleNextTextViewClick()
    }

    private fun initView() {
        viewPagerIndicator?.apply {
            adapter = viewPagerAdapter
            addOnPageChangeListener(onPageSelected = {
                pagePosition = it
            })
        }
        indicator?.apply {
            setViewPager(viewPagerIndicator)
        }
    }

    private fun handleNextTextViewClick() {
        tvNext.setOnClickListener {
            if (pagePosition < 2) {
                viewPagerIndicator.currentItem = pagePosition + 1
            } else {
                val intent = Intent(this, TabLayoutActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
