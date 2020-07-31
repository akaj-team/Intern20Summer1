package com.asiantech.intern20summer1.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.asiantech.intern20summer1.data.list

@Suppress("DEPRECATION")
class PagerAdapterFragment(fragment: FragmentManager) : FragmentPagerAdapter(fragment) {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> return list[position]
            1 -> return list[position]
            2 -> return list[position]
            else -> list[0]
        }
    }
}
