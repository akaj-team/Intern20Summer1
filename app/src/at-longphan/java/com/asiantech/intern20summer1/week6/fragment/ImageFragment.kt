package com.asiantech.intern20summer1.week6.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-longphan`.fragment_random_image_w6.*

class ImageFragment : Fragment() {

    companion object {

        private const val IMAGE_KEY = "image"

        fun newInstance(image: Int) = ImageFragment().apply {
            arguments = Bundle().apply { putInt(IMAGE_KEY, image) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_random_image_w6, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val image = arguments?.getInt(IMAGE_KEY)
        Glide.with(imgImageWeek6).load(image).into(imgImageWeek6)
    }
}
