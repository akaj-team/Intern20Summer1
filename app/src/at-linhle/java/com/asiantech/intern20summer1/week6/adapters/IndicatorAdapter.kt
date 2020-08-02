package com.asiantech.intern20summer1.week6.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.asiantech.intern20summer1.week6.fragments.IndicatorFragment

class IndicatorAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        internal const val pageNumber = 3
    }

    override fun getItem(position: Int): Fragment {
        return IndicatorFragment.newInstance(position)
    }

    override fun getCount(): Int = pageNumber
}
