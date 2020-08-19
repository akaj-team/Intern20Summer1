package com.asiantech.intern20summer1.week6.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-longphan`.fragment_step_3_w6.*

class ThirdStepFragment : Fragment() {

    companion object {

        private const val STEP_KEY = "step"

        // NewInstance constructor for creating fragment with arguments
        fun newInstance(step: String?) = ThirdStepFragment().apply {
            arguments = Bundle().apply { putString(STEP_KEY, step) }
        }
    }

    // Store instance variables
    private var step: String? = null

    // Store instance variables based on arguments passed
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        step = arguments?.getString(STEP_KEY)
    }

    // Inflate the view for the fragment based on layout XML
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_step_3_w6, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        tvStep3Description?.text = step
    }
}
