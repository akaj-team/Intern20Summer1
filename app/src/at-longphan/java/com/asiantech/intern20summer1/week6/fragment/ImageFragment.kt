package com.asiantech.intern20summer1.week6.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.bumptech.glide.Glide

class ImageFragment : Fragment() {

    companion object {

        private const val IMAGE_KEY = "image"

        fun newInstance(image: Int): ImageFragment {
            val imageFragment = ImageFragment()
            val bundle = Bundle()
            bundle.putInt(IMAGE_KEY, image)
            imageFragment.arguments = bundle
            return imageFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = inflater.inflate(R.layout.fragment_random_image_w6, container, false)

        val image = arguments?.getInt(IMAGE_KEY)
        val imageView: ImageView = view!!.findViewById(R.id.imgImageWeek6)
        Glide.with(view).load(image).into(imageView)

        return view
    }
}
