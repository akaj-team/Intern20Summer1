package com.asiantech.intern20summer1.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.asiantech.intern20summer1.fragment.FragmentOne

class PagerAdapterFragment(fragment: FragmentManager) :
    FragmentPagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object {
        private const val NUMBER_OF_PAGER = 3
    }

    override fun getCount() = NUMBER_OF_PAGER

    override fun getItem(position: Int): Fragment {
        return FragmentOne.newInstance(position)
    }
}
