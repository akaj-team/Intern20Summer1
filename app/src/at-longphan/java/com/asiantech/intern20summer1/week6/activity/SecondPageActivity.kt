package com.asiantech.intern20summer1.week6.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week6.adapter.ImageFragmentPagerAdapter
import com.asiantech.intern20summer1.week6.fragment.ImageFragment
import kotlinx.android.synthetic.`at-longphan`.activity_second_page_w6.*

class SecondPageActivity : AppCompatActivity() {

    private lateinit var adapter: ImageFragmentPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_page_w6)
        configStatusBar()
        initAdapter()
        setupViews()
    }

    private fun initAdapter() {
        adapter = ImageFragmentPagerAdapter(supportFragmentManager)
        val images = arrayOf(R.drawable.img_cat, R.drawable.img_cat2, R.drawable.img_cat3)

        val firstFragment: ImageFragment = ImageFragment.newInstance(images[(images.indices).random()])
        val secondFragment: ImageFragment = ImageFragment.newInstance(images[(images.indices).random()])
        val thirdFragment: ImageFragment = ImageFragment.newInstance(images[(images.indices).random()])

        adapter.addFragment(firstFragment, getString(R.string.home_tab_title))
        adapter.addFragment(secondFragment, getString(R.string.info_tab_title))
        adapter.addFragment(thirdFragment, getString(R.string.another_tab_title))

    }

    private fun setupViews(){
        viewPagerActivitySecondPage.adapter = adapter
        tabLayoutActivitySecondPage.setupWithViewPager(viewPagerActivitySecondPage)
    }

    private fun configStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}
