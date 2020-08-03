package com.asiantech.intern20summer1.w6.firstactivity

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.R.string.*
import com.asiantech.intern20summer1.w6.model.PagePosition
import kotlinx.android.synthetic.`at-huybui`.activity_view_pager_one.*
import kotlinx.android.synthetic.`at-huybui`.fragment_one_viewpager.*

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 07/30/20
 * This is fragment class for fragments in first activity
 */

class FragmentOne : Fragment() {

    companion object {
        private const val KEY_POS = "key"
        internal fun newInstance(position: Int) = FragmentOne().apply {
            arguments = Bundle().apply {
                putInt(KEY_POS, position)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_one_viewpager, container, false)
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {
        when (val position = arguments?.getInt(KEY_POS)) {
            PagePosition.THREE.number -> {
                tvStep?.text = getString(w6_text_step, position?.plus(1))
                (activity as Activity).tvSkip?.text = getString(w6_text_done)
            }
            else -> {
                tvStep?.text = getString(w6_text_step, position?.plus(1))
                (activity as Activity).tvSkip?.text = getString(w6_text_next)
            }
        }
    }
}
