package com.asiantech.intern20summer1.w6.secondactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-huybui`.fragment_two_view_pager.*

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 07/30/20
 * This is fragment class for fragments in second activity
 */

class FragmentTwo : Fragment() {

    companion object {
        private const val KEY_POS = "key"
        internal fun newInstance(position: Int) = FragmentTwo().apply {
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
        return inflater.inflate(R.layout.fragment_two_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        when (arguments?.getInt(KEY_POS)) {
            0 -> {
                tvNameEnglish.text = getString(R.string.w6_name_english_kangaroo)
                tvNameFrance.text = getString(R.string.w6_name_france_kangaroo)
                imgCenterMain.setImageResource(R.drawable.img_kangaroo)
            }
            1 -> {
                tvNameEnglish.text = getString(R.string.w6_name_english_koala)
                tvNameFrance.text = getString(R.string.w6_name_france_koala)
                imgCenterMain.setImageResource(R.drawable.img_koala)
            }
            2 -> {
                tvNameEnglish.text = getString(R.string.w6_name_english_Platypus)
                tvNameFrance.text = getString(R.string.w6_name_france_platypus)
                imgCenterMain.setImageResource(R.drawable.img_platypus)
            }
        }
    }
}
