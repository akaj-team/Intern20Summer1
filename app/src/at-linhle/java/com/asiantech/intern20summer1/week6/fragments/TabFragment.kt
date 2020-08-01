package com.asiantech.intern20summer1.week6.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-linhle`.fragment_tab_layout.*

class TabFragment : Fragment() {

    companion object {
        private const val KEY_POS = "key_pos"

        fun newInstance(position: Int) = TabFragment().apply {
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
        return inflater.inflate(R.layout.fragment_tab_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (arguments?.getInt(KEY_POS)) {
            0 -> Glide.with(this).load(R.drawable.img_logo).into(imgTabLayout)
            1 -> imgTabLayout.setImageResource(R.drawable.img_logo)
            2 -> imgTabLayout.setImageResource(R.drawable.img_logo)
        }
    }
}
