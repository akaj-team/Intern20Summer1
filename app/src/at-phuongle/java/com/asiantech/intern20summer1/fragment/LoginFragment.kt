package com.asiantech.intern20summer1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.SignInActivity
import kotlinx.android.synthetic.`at-phuongle`.fragment_login.*

class LoginFragment : Fragment() {
    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as SignInActivity).handleEmailEditText(edtEmail, btnLogin)
        (activity as SignInActivity).handlePasswordEditText(edtPass, btnLogin)

        tvLoginRegister.setOnClickListener {
            (activity as SignInActivity).replaceFragment(RegisterFragment.newInstance(), true)
        }
    }
}
