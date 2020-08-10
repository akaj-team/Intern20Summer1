package com.asiantech.intern20summer1.fragmennt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-vuongphan`.w7_dialog_fragment.*

class VegetableDialogFragment : DialogFragment() {
    companion object {
        internal fun newInstance(): VegetableDialogFragment {
            return VegetableDialogFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w7_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        closeDialog()
        spinner()
    }

    private fun closeDialog() {
        imgCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun spinner() {
        activity?.applicationContext?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.planets_array,
                android.R.layout.simple_list_item_1
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spnDialog.adapter = adapter
            }
        }
    }
}
