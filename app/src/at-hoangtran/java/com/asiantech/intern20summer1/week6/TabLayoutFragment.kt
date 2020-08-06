package com.asiantech.intern20summer1.week6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-hoangtran`.fragment_tab_layout.*
import java.util.*

class TabLayoutFragment : Fragment() {

    companion object {
        private const val KEY_POS = "key_pos"

        fun newInstance(position: Int) = TabLayoutFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_POS, position)
            }
        }
    }

    private val imageList = arrayListOf(
        R.mipmap.image1,
        R.mipmap.image2,
        R.mipmap.image3,
        R.mipmap.image4,
        R.mipmap.image5,
        R.mipmap.image6,
        R.mipmap.image7,
        R.mipmap.image8,
        R.mipmap.image9,
        R.mipmap.image10
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

        loadImage()
    }

    private fun randomImage() = imageList[Random().nextInt(imageList.size)]

    private fun loadImage() = Glide.with(this).load(randomImage()).into(imgTabLayout)
}
