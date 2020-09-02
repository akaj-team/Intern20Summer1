package com.asiantech.intern20summer1.w10.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w10.activity.ApiMainActivity
import kotlinx.android.synthetic.`at-huybui`.w10_fragment_sign_in.*

class SignInFragment : Fragment() {

    companion object {
        internal fun newInstance() = SignInFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w10_fragment_sign_in,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        initListenerButton()
    }

    private fun initListenerButton(){
        tvRegister_SignIn_w10?.setOnClickListener {
            (activity as ApiMainActivity).addFragment(SignUpFragment.newInstance(),true)
        }

        btnSignIn_SignIn_w10?.setOnClickListener {
            if(isCheckAccount()){
                (activity as ApiMainActivity).replaceFragment(HomeFragment.newInstance())
            }
        }
    }

    private fun isCheckAccount() : Boolean{
        return true
    }
}
