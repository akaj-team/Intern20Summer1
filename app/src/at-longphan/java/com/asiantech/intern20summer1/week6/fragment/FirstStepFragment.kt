package com.asiantech.intern20summer1.week6.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R

class FirstStepFragment : Fragment() {

    companion object {

        private const val PAGE_KEY = "page"
        private const val STEP_KEY = "step"

        var onTextViewNextClicked: () -> Unit = {}

        // newInstance constructor for creating fragment with arguments
        fun newInstance(page: Int, step: String?): FirstStepFragment {
            val step1Fragment = FirstStepFragment()
            val args = Bundle()
            args.putInt(PAGE_KEY, page)
            args.putString(STEP_KEY, step)
            step1Fragment.arguments = args
            return step1Fragment
        }
    }

    // Store instance variables
    private var page = 0
    private var step: String? = null

    // Store instance variables based on arguments passed
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments!!.getInt(PAGE_KEY)
        step = arguments!!.getString(STEP_KEY)
    }

    // Inflate the view for the fragment based on layout XML
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_step_1_2, container, false)

        val tvStep = view.findViewById(R.id.tvStepDescription) as? TextView
        tvStep?.text = step

        val tvNext = view.findViewById(R.id.tvNextStep1And2) as? TextView
        tvNext?.setOnClickListener {
            onTextViewNextClicked.invoke()
        }

        return view
    }
}
