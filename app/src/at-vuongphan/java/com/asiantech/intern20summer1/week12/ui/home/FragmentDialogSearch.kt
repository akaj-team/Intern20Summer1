package com.asiantech.intern20summer1.week12.ui.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-vuongphan`.w10_dialog_search.*

class FragmentDialogSearch : DialogFragment() {
    lateinit var view: NewFeedFragment

    companion object {
        fun newInstance(parent: NewFeedFragment) = FragmentDialogSearch().apply {
            view = parent
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w10_dialog_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBackClicked()
        initView()
        initButtonOkClicked()
    }

    private fun initBackClicked() {
        imgBack?.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun initView() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    private fun initButtonOkClicked() {
        btnOk?.setOnClickListener {
            val search = edtSearch.text.toString()
            view.search(search)
            dialog?.dismiss()
        }
    }
}
