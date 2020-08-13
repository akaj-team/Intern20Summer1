package com.asiantech.intern20summer1.week6.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week6.adapter.FirstViewPagerAdapter
import kotlinx.android.synthetic.`at-phuongle`.activity_first.*

class FirstActivity : AppCompatActivity() {
    private lateinit var steps: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        init()
    }

    private fun init() {
        steps = arrayListOf(
            getString(R.string.step_1_text_view_description),
            getString(R.string.step_2_text_view_description),
            getString(R.string.step_3_text_view_description)
        )

        initViewPager()
        handleNextTextViewListener()
    }

    private fun initViewPager() {
        viewPager?.apply {
            adapter = FirstViewPagerAdapter(supportFragmentManager, steps)
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    when (position) {
                        steps.size - 1 -> tvNext.text =
                            getString(R.string.skip_text_view_description)
                        else -> tvNext.text = getString(R.string.next_text_view_description)
                    }
                }
            })
        }
    }

    private fun handleNextTextViewListener() {
        tvNext.setOnClickListener {
            if (viewPager?.currentItem == steps.size - 1) {
                val intent =
                    Intent(this@FirstActivity, SecondActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                viewPager?.setCurrentItem(viewPager.currentItem + 1, true)
            }
        }
    }
}
