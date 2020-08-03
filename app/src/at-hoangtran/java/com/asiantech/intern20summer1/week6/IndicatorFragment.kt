package com.asiantech.intern20summer1.week6

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.fragment_indicator.*

class IndicatorFragment : Fragment() {

    companion object {
        private const val KEY_POS = "key_pos"
        fun newInstance(position: Int) = IndicatorFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_POS, position + 1)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_indicator, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvIndicator.text = "Step ${arguments?.getInt(KEY_POS)}"
    }
}
