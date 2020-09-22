package com.asiantech.intern20summer1.week12

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.fragment_search_dialog.*

class SearchDialogFragment : DialogFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        handleBackButtonClicked()
        handleClickingOkButton()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_dialog, container, false)
    }

    private fun handleBackButtonClicked() {
        btnCancel?.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun initView() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun handleClickingOkButton() {
        btnSearch?.setOnClickListener {
            val search = edtSearch?.text.toString()
            HomeFragment().search(search)
            dialog?.dismiss()
        }
    }
}
