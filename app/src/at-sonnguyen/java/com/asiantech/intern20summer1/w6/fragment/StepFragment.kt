package com.asiantech.intern20summer1.w6.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-sonnguyen`.w6_fragment_step.*

class StepFragment : Fragment() {
    private var position = 0

    companion object {
        private const val KEY_VALUE = " position"
        fun newInstance(position: Int): StepFragment {
            val fragment = StepFragment()
            fragment.arguments = Bundle().apply {
                putInt(KEY_VALUE, position)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w6_fragment_step, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = arguments?.getInt(KEY_VALUE) ?: 0
        tvFragmentStep.text = getString(R.string.w6_step_fragment_text_view, position + 1)
    }
}
