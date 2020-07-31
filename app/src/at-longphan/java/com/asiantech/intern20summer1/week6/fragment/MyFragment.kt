package com.asiantech.intern20summer1.week6.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.bumptech.glide.Glide

class MyFragment : Fragment() {

    companion object {
        fun newInstance(image: Int): MyFragment {

            val fragment = MyFragment()

            val bundle = Bundle(1)

            bundle.putInt("image", image)

            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = inflater.inflate(R.layout.fragment_home, container, false)

        val image = arguments?.getInt("image")

        val imageView: ImageView = view!!.findViewById(R.id.imgImageWeek6)
        Glide.with(view).load(image).into(imageView)

        return view
    }
}
