package com.asiantech.intern20summer1.fragment.w5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.R.string.text_step
import kotlinx.android.synthetic.`at-vuongphan`.fragment_one.*

class FragmentOne : Fragment() {
    companion object {
        private const val KEY_POS = "key"
        internal fun newInstance(position: Int) = FragmentOne().apply {
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
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        when (val position = arguments?.getInt(KEY_POS)) {
            PagePosition.THREE.number -> {
                tvName?.text = getString(text_step, position?.plus(1))
            }
            else -> {
                tvName?.text = getString(text_step, position?.plus(1))
            }
        }
    }
}

enum class PagePosition(val number: Int?) {
    THREE(2),
}
