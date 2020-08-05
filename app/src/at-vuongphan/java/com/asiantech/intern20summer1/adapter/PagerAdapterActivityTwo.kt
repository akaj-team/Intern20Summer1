package com.asiantech.intern20summer1.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.fragmennt.FragmentTwo

class PagerAdapterActivityTwo(fragment: FragmentManager) : FragmentPagerAdapter(
    fragment,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getCount() = 3
    override fun getItem(position: Int): Fragment {
        return FragmentTwo.newInstance(position)
    }
}
