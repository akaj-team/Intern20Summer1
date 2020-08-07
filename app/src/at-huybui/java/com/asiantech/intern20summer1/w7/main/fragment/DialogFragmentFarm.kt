package com.asiantech.intern20summer1.w7.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-huybui`.fragment_dialog.*

open class DialogFragmentFarm : DialogFragment() {

    companion object {
        internal fun newInstance() = DialogFragmentFarm()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        handleListenerForButtonBack()
    }

    private fun handleListenerForButtonBack() {
        imgBackIconDialog.setOnClickListener {
            dialog?.dismiss()
        }
    }
}
