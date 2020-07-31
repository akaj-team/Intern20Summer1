package com.asiantech.intern20summer1.w6.secondactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-huybui`.fragment_two_view_pager.*

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 07/30/20
 * This is fragment class for fragments in second activity
 */

class FragmentTwo : Fragment() {

    companion object {
        private const val KEY_POS = "key"
        private const val URL_IMAGE_ANIMAL_ONE =
            "https://cdn.imgbin.com/16/25/9/imgbin-cartoon-kangaroo-illustration-brown-cartoon-kangaroo-0fXYNL6zCPNkHTrW1ji9qCTgi.jpg"
        private const val URL_IMAGE_ANIMAL_TWO =
            "https://i.pinimg.com/originals/a4/7d/8f/a47d8ff476cc7edb8e988e1977cad282.jpg"
        private const val URL_IMAGE_ANIMAL_THREE =
            "https://i.pinimg.com/originals/44/51/e8/4451e8f6f5e1f542e88b9911a48106b3.png"

        internal fun newInstance(position: Int) = FragmentTwo().apply {
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
        return inflater.inflate(R.layout.fragment_two_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (arguments?.getInt(KEY_POS)) {
            0 -> {
                tvNameEnglish.text = getString(R.string.w6_name_english_kangaroo)
                tvNameFrance.text = getString(R.string.w6_name_france_kangaroo)
                Glide.with(context)
                    .load(URL_IMAGE_ANIMAL_ONE)
                    .into(imgCenterMain)
            }
            1 -> {
                tvNameEnglish.text = getString(R.string.w6_name_english_koala)
                tvNameFrance.text = getString(R.string.w6_name_france_koala)
                Glide.with(context)
                    .load(URL_IMAGE_ANIMAL_TWO)
                    .into(imgCenterMain)
            }
            2 -> {
                tvNameEnglish.text = getString(R.string.w6_name_english_Platypus)
                tvNameFrance.text = getString(R.string.w6_name_france_platypus)
                Glide.with(context)
                    .load(URL_IMAGE_ANIMAL_THREE)
                    .into(imgCenterMain)
            }
        }
    }
}
