package com.asiantech.intern20summer1.w6.firstactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w6.model.PagePosition
import com.asiantech.intern20summer1.w6.secondactivity.ViewPagerTwoActivity
import kotlinx.android.synthetic.`at-huybui`.activity_view_pager_one.*


/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 07/30/20
 * This is class for first activity
 */

class ViewPagerOneActivity : AppCompatActivity() {

    private val viewPagerAdapter by lazy { ViewPagerOneAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_one)
        initView()
        handleListener()
    }

    private fun initView() {
        viewpagerOne?.apply {
            adapter = viewPagerAdapter
        }
    }

    private fun handleListener() {
        tvSkip?.setOnClickListener {
            if (viewpagerOne?.currentItem == PagePosition.THREE.number) {
                val intent = Intent(this, ViewPagerTwoActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                viewpagerOne?.setCurrentItem(viewpagerOne.currentItem + 1, true)
            }
        }
    }
}
