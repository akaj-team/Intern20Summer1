package com.asiantech.intern20summer1.w12.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R

class SearchDialogFragment : DialogFragment(){
    companion object{
        internal fun newInstance() = SearchDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w12_fragment_search_dialog,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
//    private fun handleSearchImageViewListener(){
//        imgSearch.setOnClickListener {
//            (activity as? HomeActivity)?.replaceFragmentHome(HomeFragment.newInstance(HomeActivity.))
//        }
//    }
}