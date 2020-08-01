package com.asiantech.intern20summer1.week6.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.asiantech.intern20summer1.week6.fragments.TabFragment

class TabLayoutAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return TabFragment.newInstance(position)
    }

    override fun getCount(): Int = 3
}
