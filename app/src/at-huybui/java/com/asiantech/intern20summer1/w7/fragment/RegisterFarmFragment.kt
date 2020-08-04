package com.asiantech.intern20summer1.w7.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/04/20
 * This is fragment class for register fragments
 */

class RegisterFarmFragment : Fragment() {

    companion object {
        internal fun newInstance() = RegisterFarmFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_farm, container, false)
    }
}
