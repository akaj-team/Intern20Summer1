package com.asiantech.intern20summer1.week6.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.asiantech.intern20summer1.week6.fragment.AnimalFragment

class SecondViewPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object {
        const val PAGE_SIZE = 3
    }

    override fun getItem(position: Int): Fragment {
        return AnimalFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return PAGE_SIZE
    }
}