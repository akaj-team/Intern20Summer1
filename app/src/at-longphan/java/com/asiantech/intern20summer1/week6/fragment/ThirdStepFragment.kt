package com.asiantech.intern20summer1.week6.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week6.activity.SecondPageActivity

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
        val view: View = inflater.inflate(R.layout.fragment_step_3, container, false)
        val tvStep = view.findViewById(R.id.tvStepDescription) as? TextView
        tvStep?.text = step

        val tvNext = view.findViewById(R.id.tvNextStep3) as? TextView
        tvNext?.setOnClickListener {
            val intent = Intent(activity, SecondPageActivity::class.java)
            startActivity(intent)
            this.activity?.finish()
        }
        return view
    }
}
