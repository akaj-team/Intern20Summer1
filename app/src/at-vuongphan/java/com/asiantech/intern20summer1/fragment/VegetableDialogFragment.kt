package com.asiantech.intern20summer1.fragment

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.database.Plant
import com.asiantech.intern20summer1.database.VegetableDB
import kotlinx.android.synthetic.`at-vuongphan`.w7_dialog_fragment.*

class VegetableDialogFragment : DialogFragment() {
    private var plantSelected: Plant? = null
    private var listPlants: List<Plant>? = null
    private var dataBase: VegetableDB? = null

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
        dataBase = VegetableDB.dataBaseConnect(requireContext())
        initView()
        spinner()
        //  initData()
    }

    private fun initView() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        closeDialog()
        // initButtonOk()
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
        @Suppress("DEPRECATION")
        spnDialog.background.setColorFilter(
            resources.getColor(R.color.colorRed),
            PorterDuff.Mode.SRC_ATOP
        )
    }
}
