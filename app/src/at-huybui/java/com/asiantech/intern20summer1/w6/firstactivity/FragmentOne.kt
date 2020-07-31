package com.asiantech.intern20summer1.w6.firstactivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.R.string.w6_text_step
import com.asiantech.intern20summer1.w6.secondactivity.ViewPagerTwoActivity
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (val position = arguments?.getInt(KEY_POS)?.plus(1)) {
            1, 2 -> {
                tvCenterFm1W6.text = getString(w6_text_step) + position
                tvSkipFm1W6.text = getString(R.string.w6_text_next)
                tvSkipFm1W6.setOnClickListener {
                    (activity as ViewPagerOneActivity).handleNextFragment()
                }
            }
            3 -> {
                tvCenterFm1W6.text = getString(w6_text_step) + position
                tvSkipFm1W6.text = getString(R.string.w6_text_done)
                tvSkipFm1W6.setOnClickListener {
                    val intent = Intent(context, ViewPagerTwoActivity::class.java)
                    startActivity(intent)
                    (activity as ViewPagerOneActivity).finish()
                }
            }
        }
    }
}
