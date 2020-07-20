package com.asiantech.intern20summer1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-linhle`.fragment_signup.*

class SignUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleClickingArrowLeft()
    }

    private fun handleClickingArrowLeft(){
        imgArrowLeft.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }
}
