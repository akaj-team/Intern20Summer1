package com.asiantech.intern20summer1.week10.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.fragment.PlantNewDialogFragment
import kotlinx.android.synthetic.`at-longphan`.fragment_new_post_w10.*

class NewPostFragment: Fragment() {

    companion object {
        internal fun newInstance() = PlantNewDialogFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_post_w10, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgBackNewPost?.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }
}