package com.asiantech.intern20summer1.week6.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-linhle`.fragment_tab_layout.*
import kotlin.random.Random

class TabFragment : Fragment() {

    companion object {
        private const val KEY_POS = "key_pos"

        fun newInstance(position: Int) = TabFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_POS, position)
            }
        }
    }

    private val imageList = arrayListOf(
        R.drawable.img_01,
        R.drawable.img_02,
        R.drawable.img_03,
        R.drawable.img_04,
        R.drawable.img_05
    )

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
            0 -> loadImage()
            1 -> loadImage()
            2 -> loadImage()
        }
    }

    private fun randomImage() = imageList[Random.nextInt(0, imageList.size)]

    private fun loadImage() = Glide.with(this).load(randomImage()).into(imgTabLayout)
}
