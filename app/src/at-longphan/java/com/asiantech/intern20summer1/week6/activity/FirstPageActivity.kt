package com.asiantech.intern20summer1.week6.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week6.adapter.StepFragmentPagerAdapter
import com.asiantech.intern20summer1.week6.fragment.FirstStepFragment
import com.asiantech.intern20summer1.week6.fragment.ThirdStepFragment
import kotlinx.android.synthetic.`at-longphan`.activity_first_page_w6.*

class FirstPageActivity : AppCompatActivity() {

    private lateinit var adapter: StepFragmentPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page_w6)
        configStatusBar()
        setupAdapter()
        setupIndicator()
        handleTextViewNextClickListener()
    }

    private fun configStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun setupAdapter() {
        adapter = StepFragmentPagerAdapter(supportFragmentManager)
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

        viewPagerActivityFirstPage.adapter = adapter
    }

    private fun setupIndicator() {
        val sizeIndicator = resources.getDimensionPixelOffset(R.dimen.size_indicator)
        val sizeIndicatorSelected = resources.getDimensionPixelOffset(R.dimen.size_indicator_selected)
        viewPagerActivityFirstPage?.let {
            indicatorViewActivityFirstPage
                .setIndicatorGap(resources.getDimensionPixelOffset(R.dimen.space_size_indicator))
                .setIndicatorDrawable(R.drawable.ic_cat_indicator, R.drawable.ic_cat_indicator_selected)
                .setIndicatorSize(sizeIndicator, sizeIndicator, sizeIndicatorSelected, sizeIndicatorSelected)
                .setupWithViewPager(it)
        }
    }

    private fun handleTextViewNextClickListener(){
        tvNextDescription?.setOnClickListener {
            when(viewPagerActivityFirstPage.currentItem){
                2 -> {
                    startActivity(Intent(this, SecondPageActivity::class.java))
                    finish()
                }
                else -> viewPagerActivityFirstPage.currentItem++
            }
        }
    }
}
