package com.asiantech.intern20summer1.fragmennt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R

class VegetableDetailFragment : Fragment() {
    companion object {
        internal fun newInstance(): VegetableDetailFragment {
            return VegetableDetailFragment()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w7_vegetable_detail, container, false)
    }
}
