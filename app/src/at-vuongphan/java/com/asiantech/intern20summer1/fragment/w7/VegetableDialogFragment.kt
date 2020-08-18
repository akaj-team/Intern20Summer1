package com.asiantech.intern20summer1.fragment.w7

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.database.Cultivation
import com.asiantech.intern20summer1.database.Plant
import com.asiantech.intern20summer1.database.VegetableDB
import kotlinx.android.synthetic.`at-vuongphan`.w7_dialog_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class VegetableDialogFragment : DialogFragment() {
    private var plantSelected: Plant? = null
    private var listPlants: List<Plant>? = null
    private var dataBase: VegetableDB? = null

    companion object {
        internal const val FORMAT_CODE_DATE = "dd/MM/yyyy HH:mm"
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
        initData()
    }

    private fun initView() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        closeDialog()
        initButtonOk()
    }

    private fun closeDialog() {
        imgCancel.setOnClickListener {
            dismiss()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun initButtonOk() {
        btnOk?.setOnClickListener {
            plantSelected?.let {
                Cultivation().apply {
                    val dateFormat = SimpleDateFormat(FORMAT_CODE_DATE)
                    val dateCurrent = dateFormat.format(Date())
                    plantId = it.plantId
                    dateCultivation = dateCurrent
                    dateWatering = dateCurrent
                    dataBase?.cultivationDao()?.addCultivation(this)
                }
                Toast.makeText(
                    context,
                    resources.getString(R.string.toast_description),
                    Toast.LENGTH_SHORT
                ).show()
                dialog?.dismiss()
            }
        }
    }

    private fun initData() {
        listPlants = dataBase?.plantDao()?.getAllPlant()
        val arrayNamePlant = arrayListOf<String>()
        listPlants?.forEach {
            it.name?.let { it ->
                arrayNamePlant.add(it)
            }
        }
        val adapterSpinner =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayNamePlant)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spnDialog?.adapter = adapterSpinner
        spnDialog?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                listPlants?.get(position)?.let {
                    plantSelected = it
                }
            }
        }
    }
}
