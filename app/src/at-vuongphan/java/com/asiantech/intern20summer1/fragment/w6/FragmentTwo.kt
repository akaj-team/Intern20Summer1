package com.asiantech.intern20summer1.fragment.w6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.data.ensign
import com.asiantech.intern20summer1.data.image
import kotlinx.android.synthetic.`at-vuongphan`.fragment_two.*

class FragmentTwo : Fragment() {
    companion object {
        private const val KEY_POS = "key"
        internal fun newInstance(position: Int) = FragmentTwo()
            .apply {
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
        return inflater.inflate(R.layout.fragment_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        when (arguments?.getInt(KEY_POS)) {
            0 -> {
                getData()
                tvNameIconOne?.text = resources.getString(R.string.portugal_text_view_description)
                tvNameIconTwo?.text = resources.getString(R.string.portugal_text_view_description)
            }
            1 -> {
                getData()
                tvNameIconOne?.text = resources.getString(R.string.viet_nam_text_view_description)
                tvNameIconTwo?.text = resources.getString(R.string.viet_nam_text_view_description)
            }
            2 -> {
                getData()
                tvNameIconOne?.text = resources.getString(R.string.my_text_view_description)
                tvNameIconTwo?.text = resources.getString(R.string.my_text_view_description)
            }
        }
    }

    private fun getData() {
        imgIconOne?.setImageResource(ensign.random())
        imgIconTwo?.setImageResource(ensign.random())
        imgViewCenter?.setImageResource(image.random())
    }
}
