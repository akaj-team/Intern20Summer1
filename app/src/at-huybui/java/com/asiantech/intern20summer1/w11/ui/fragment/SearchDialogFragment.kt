package com.asiantech.intern20summer1.w11.ui.fragment

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-huybui`.w10_dialog_fragment_search.*

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 01/09/2020.
 * This is UpdateDialogFragment class. It is fragment to display update the post page
 */

class SearchDialogFragment : DialogFragment() {

    companion object {
        internal fun newInstance() = SearchDialogFragment()
    }

    internal var onSearchClick: (text: String) -> Unit = { String }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        d("permissionx", "open")
        return inflater.inflate(R.layout.w10_dialog_fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        btnSearch.setOnClickListener {
            val text = edtContent?.text.toString()
            onSearchClick.invoke(text)
            dialog?.dismiss()
        }

        btnBack?.setOnClickListener {
            dialog?.dismiss()
        }
    }

}
