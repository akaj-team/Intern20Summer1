package com.asiantech.intern20summer1.week6.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.asiantech.intern20summer1.week6.fragment.StepFragment

class FirstViewPagerAdapter(fm: FragmentManager, private val steps: List<String>) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return StepFragment.newInstance(steps[position])
    }

    override fun getCount(): Int {
        return steps.size
    }
}
