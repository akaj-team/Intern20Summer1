package com.asiantech.intern20summer1.week6.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-phuongle`.fragment_step.*

class StepFragment : Fragment() {
    companion object {
        private const val KEY_STEP = "key_step"

        fun newInstance(step: String) = StepFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_STEP, step)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleStepTextView()
    }

    private fun handleStepTextView() {
        tvStep.text = arguments?.getString(KEY_STEP)
    }
}
