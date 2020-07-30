package com.asiantech.intern20summer1.w6.firstactivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 07/30/20
 * This is class for adapter of view pager in first activity
 */

class ViewPagerOneAdapter(fm: FragmentManager) : FragmentPagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    companion object {
        private const val NUMBER_OF_PAGER = 3
    }

    override fun getItem(position: Int): Fragment {
        return  FragmentOne.newInstance(position)
    }

    override fun getCount() = NUMBER_OF_PAGER
}