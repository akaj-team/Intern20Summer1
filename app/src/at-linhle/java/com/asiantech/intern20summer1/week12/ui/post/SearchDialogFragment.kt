package com.asiantech.intern20summer1.week12.ui.post

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-linhle`.fragment_dialog_search.*

class SearchDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        handleBackButtonClicked()
        handleClickingOkButton()
    }

    private fun handleBackButtonClicked() {
        imgCancel?.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun initView() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun handleClickingOkButton() {
        btnOkDialog?.setOnClickListener {
            val search = edtSearch?.text.toString()
            (activity as? HomeRxActivity)?.search(search)
            dialog?.dismiss()
        }
    }
}
