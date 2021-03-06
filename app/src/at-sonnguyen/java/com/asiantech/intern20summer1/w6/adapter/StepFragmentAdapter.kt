package com.asiantech.intern20summer1.w6.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.asiantech.intern20summer1.w6.fragment.StepFragment

class StepFragmentAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object {
        private const val STEP_NUMBER = 3
    }

    override fun getItem(position: Int) = StepFragment.newInstance(position)

    override fun getCount() = STEP_NUMBER

    override fun getItemPosition(`object`: Any) = PagerAdapter.POSITION_NONE
}
