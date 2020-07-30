package com.asiantech.intern20summer1.w6.secondactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 07/30/20
 * This is fragment class for fragments in second activity
 */

class FragmentTwo : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_two_viewpager, container, false)
    }
}