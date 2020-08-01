package com.asiantech.intern20summer1.w6.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-sonnguyen`.w6_home_fragment.*
import kotlin.random.Random

class HomeFragment : Fragment() {

    private var position = 0
    private var imageList = arrayOf(
        R.drawable.img_harry_maguire,
        R.drawable.img_bruno_fernandes,
        R.drawable.img_de_gea,
        R.drawable.img_martial,
        R.drawable.img_greenwood,
        R.drawable.img_pogba,
        R.drawable.img_pogba_2,
        R.drawable.img_lindelof,
        R.drawable.img_luke_shaw,
        R.drawable.img_marcus,
        R.drawable.img_matic,
        R.drawable.img_mctominay,
        R.drawable.img_wanbi
    )

    companion object {
        private const val KEY_VALUE = "position"
        fun newInstance(position: Int): HomeFragment {
            val fragment = HomeFragment()
            fragment.arguments = Bundle().apply {
                putInt(KEY_VALUE, position)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w6_home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        position = arguments?.getInt(KEY_VALUE) ?: 0
        when (position) {
            0 -> imgHomeFragment.setImageResource(imageList[Random.nextInt(1, imageList.size)])
            1 -> imgHomeFragment.setImageResource(imageList[Random.nextInt(1, imageList.size)])
            2 -> imgHomeFragment.setImageResource(imageList[Random.nextInt(1, imageList.size)])
        }
    }
}
