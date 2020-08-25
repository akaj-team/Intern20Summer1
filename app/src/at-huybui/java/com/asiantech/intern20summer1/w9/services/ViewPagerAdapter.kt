package com.asiantech.intern20summer1.w9.services

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.asiantech.intern20summer1.w9.fragments.MusicFragment
import com.asiantech.intern20summer1.w9.fragments.PlayerFragment
import com.asiantech.intern20summer1.w9.models.Song

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 07/30/20
 * This is class for adapter of view pager in music activity
 */

class ViewPagerAdapter(
    fm: FragmentManager,
    private val songLists: MutableList<Song>
) : FragmentPagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    companion object {
        private const val NUMBER_OF_PAGER = 2
    }

    override fun getItem(position: Int): Fragment {

        return if (position == 0) {
            MusicFragment.newInstance(songLists)
        } else {
            PlayerFragment.newInstance(songLists)
        }
    }
    override fun getCount() = NUMBER_OF_PAGER
}
