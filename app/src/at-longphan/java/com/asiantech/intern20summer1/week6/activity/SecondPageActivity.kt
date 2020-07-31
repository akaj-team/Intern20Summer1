package com.asiantech.intern20summer1.week6.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week6.adapter.MyFragmentPagerAdapter
import com.asiantech.intern20summer1.week6.fragment.MyFragment
import com.google.android.material.tabs.TabLayout

class SecondPageActivity : AppCompatActivity() {

    private lateinit var viewpager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_page)
        configStatusBar()
        initViews()
        setupViewPager()
    }

    private fun initViews() {
        tabs = findViewById(R.id.tabs)
        viewpager = findViewById(R.id.viewpager)
    }

    private fun setupViewPager() {
        val adapter = MyFragmentPagerAdapter(supportFragmentManager)

        val images = arrayOf(R.drawable.img_cat, R.drawable.img_cat2, R.drawable.img_cat3)

        val firstFragment: MyFragment = MyFragment.newInstance(images[(images.indices).random()])
        val secondFragment: MyFragment = MyFragment.newInstance(images[(images.indices).random()])
        val thirdFragment: MyFragment = MyFragment.newInstance(images[(images.indices).random()])

        adapter.addFragment(firstFragment, "Home")
        adapter.addFragment(secondFragment, "Info")
        adapter.addFragment(thirdFragment, "Another")

        viewpager.adapter = adapter

        tabs.setupWithViewPager(viewpager)
    }

    private fun configStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}
