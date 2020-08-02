package com.asiantech.intern20summer1.week6.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week6.adapter.StepFragmentPagerAdapter
import com.asiantech.intern20summer1.week6.fragment.FirstStepFragment
import com.asiantech.intern20summer1.week6.fragment.ThirdStepFragment
import com.zhpan.indicator.DrawableIndicator

class FirstPageActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var indicatorView: DrawableIndicator
    private lateinit var adapter: StepFragmentPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)
        configStatusBar()
        initViews()
        setupAdapter()
        setupIndicator()
        handleTextViewNextClickListener()
    }

    private fun configStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun initViews() {
        viewPager = findViewById(R.id.viewpager)
        adapter = StepFragmentPagerAdapter(supportFragmentManager)
        indicatorView = findViewById(R.id.indicator_view)
    }

    private fun setupAdapter() {
        val step1Fragment = FirstStepFragment.newInstance(
            0,
            getString(R.string.text_view_step_description_fragment_step_1)
        )
        val step2Fragment = FirstStepFragment.newInstance(
            1,
            getString(R.string.text_view_step_description_fragment_step_2)
        )
        val step3Fragment =
            ThirdStepFragment.newInstance(getString(R.string.text_view_step_description_fragment_step_3))

        adapter.addFragment(step1Fragment)
        adapter.addFragment(step2Fragment)
        adapter.addFragment(step3Fragment)

        viewPager.adapter = adapter
    }

    private fun setupIndicator() {
        val sizeIndicator = resources.getDimensionPixelOffset(R.dimen.size_indicator)
        val sizeIndicatorSelected = resources.getDimensionPixelOffset(R.dimen.size_indicator_selected)
        viewPager.let {
            indicatorView
                .setIndicatorGap(resources.getDimensionPixelOffset(R.dimen.space_size_indicator))
                .setIndicatorDrawable(R.drawable.ic_cat_indicator, R.drawable.ic_cat_indicator_selected)
                .setIndicatorSize(sizeIndicator, sizeIndicator, sizeIndicatorSelected, sizeIndicatorSelected)
                .setupWithViewPager(it)
        }
    }

    private fun handleTextViewNextClickListener(){
        FirstStepFragment.onTextViewNextClicked = {
            viewPager.currentItem++
        }
    }
}
