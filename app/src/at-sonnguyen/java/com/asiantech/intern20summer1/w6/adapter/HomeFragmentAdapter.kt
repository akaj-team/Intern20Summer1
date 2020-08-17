package com.asiantech.intern20summer1.w6.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.asiantech.intern20summer1.w6.fragment.HomeFragment

class HomeFragmentAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object {
        private const val TAB_NUMBER = 3
    }

    override fun getItem(position: Int) = HomeFragment.newInstance(position)

    override fun getCount() = TAB_NUMBER

    override fun getItemPosition(`object`: Any) = PagerAdapter.POSITION_NONE
}
