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
        fun newInstance(step: String?): ThirdStepFragment {
            val fragmentFirst = ThirdStepFragment()
            val args = Bundle()
            args.putString(STEP_KEY, step)
            fragmentFirst.arguments = args
            return fragmentFirst
        }
    }

    // Store instance variables
    private var step: String? = null

    // Store instance variables based on arguments passed
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        step = arguments!!.getString(STEP_KEY)
    }

    // Inflate the view for the fragment based on layout XML
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_step_3_w6, container, false)

        tvStep3Description?.text = step

        return view
    }
}
