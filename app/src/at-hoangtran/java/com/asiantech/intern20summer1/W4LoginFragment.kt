package com.asiantech.intern20summer1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.`at-hoangtran`.w4_login_fragment.*

class W4LoginFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w4_login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleSignUp()

    }

    private fun handleSignUp() {
        tv_signUp.setOnClickListener {
            val trans = fragmentManager?.beginTransaction()
            trans?.replace(R.id.fl_container, W4SignUpFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }


}
