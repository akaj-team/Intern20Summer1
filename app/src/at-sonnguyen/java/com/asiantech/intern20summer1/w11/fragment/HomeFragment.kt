package com.asiantech.intern20summer1.w11.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w11.activity.HomeActivity
import kotlinx.android.synthetic.`at-sonnguyen`.w11_fragment_home.*

class HomeFragment : Fragment(){
    companion object{
        internal fun newInstance()= HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w11_fragment_home,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }
    private fun initListener(){
        btnChessBoard.setOnClickListener {
            (activity as? HomeActivity)?.replaceFragment(ChessBoardFragment.newInstance())
        }
    }
}
