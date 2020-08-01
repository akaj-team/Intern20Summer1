package com.asiantech.intern20summer1.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.adapter.PagerAdapterActivityTwo
import com.asiantech.intern20summer1.data.ensign
import com.asiantech.intern20summer1.data.image
import kotlinx.android.synthetic.`at-vuongphan`.activity_view_pager_two.*

class ViewPagerActivityTwo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_two)
        initViews()
    }

    private fun initViews() {
        viewPager2?.apply {
            adapter=
                PagerAdapterActivityTwo(
                    getImageView(),
                    getIconEnsign(),
                    getNameTitleTextView(),
                    context
                )
            tabLayout.setupWithViewPager(this)
        }
    }

    private fun getNameTitleTextView(): List<String> {
        return listOf(
            resources.getString(R.string.portugal_text_view_description),
            resources.getString(R.string.viet_nam_text_view_description),
            resources.getString(R.string.my_text_view_description)
        )
    }

    private fun getIconEnsign(): List<Int> {
        return ensign
    }

    private fun getImageView(): List<Int> {
        return listOf(
            image.random(), image.random(), image.random()
        )
    }
}
