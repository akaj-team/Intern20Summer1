package com.asiantech.intern20summer1.week6.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.asiantech.intern20summer1.week6.fragment.FragmentTwo

class PagerAdapterActivityTwo(fragment: FragmentManager) : FragmentPagerAdapter(
    fragment,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    companion object {
        private const val NUMBER_PAGER = 3
    }

    override fun getCount() = NUMBER_PAGER
    override fun getItem(position: Int): Fragment {
        return FragmentTwo.newInstance(position)
    }
}
