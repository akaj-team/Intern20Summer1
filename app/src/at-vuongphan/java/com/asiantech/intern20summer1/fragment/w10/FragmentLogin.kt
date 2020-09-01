package com.asiantech.intern20summer1.fragment.w10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.w10.ActivityLogin
import kotlinx.android.synthetic.`at-vuongphan`.w10_fragment_login.*

class FragmentLogin : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w10_fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTextViewRegister()
    }

    private fun initTextViewRegister() {
        tvCreateNewAccount?.setOnClickListener {
            (activity as? ActivityLogin)?.openFragment(FragmentRegister(), true)
        }
    }
}
