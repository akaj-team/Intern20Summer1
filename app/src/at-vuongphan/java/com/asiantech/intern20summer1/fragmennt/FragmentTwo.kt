package com.asiantech.intern20summer1.fragmennt

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R

class FragmentTwo : Fragment() {
    companion object {
        internal fun newInstance(): Fragment {
            return FragmentTwo()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_one, container, false)
        val name = view.findViewById(R.id.tvName) as? TextView
        val fr = view.findViewById<FrameLayout>(R.id.frMain)
        fr.setBackgroundColor(Color.CYAN)
        name?.text = resources.getString(R.string.text_view_fragment_two_description_text)
        return view
    }
}
