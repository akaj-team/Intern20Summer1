package com.asiantech.intern20summer1.w7.main.fragment

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.database.ConnectDataBase
import com.asiantech.intern20summer1.w7.main.MainFarmActivity
import com.asiantech.intern20summer1.w7.main.companion.AppCompanion
import com.asiantech.intern20summer1.w7.model.CultivationModel
import com.asiantech.intern20summer1.w7.model.PlantModel
import kotlinx.android.synthetic.`at-huybui`.fragment_dialog.*
import java.text.SimpleDateFormat
import java.util.*

open class DialogFragmentFarm : DialogFragment() {

    companion object {
        internal fun newInstance() = DialogFragmentFarm()
    }

    private var plantSelected: PlantModel? = null
    private var listPlants: List<PlantModel>? = null
    private var dataBase: ConnectDataBase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBase = ConnectDataBase.dataBaseConnect(requireContext())
        initView()
        initData()
    }

    private fun initView() {
        handleListenerForButtonBack()
        handleListenerForButtonOk()
    }

    private fun handleListenerForButtonBack() {
        imgBackIconDialog.setOnClickListener {
            val plant = spinnerTree?.selectedItemPosition?.let { it1 -> listPlants?.get(it1) }
            val cultivation = CultivationModel()
            cultivation.plantId = plant?.plantId
            dataBase?.cultivationDao()?.addCultivation(cultivation)
            dialog?.dismiss()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun handleListenerForButtonOk() {
        btnOkDialog?.setOnClickListener {
            val dateFormat = SimpleDateFormat(AppCompanion.FORMAT_CODE_DATE)
            val dateCurrent = dateFormat.format(Date())
            plantSelected?.let {
                val cultivationNew = CultivationModel()
                cultivationNew.plantId = it.plantId.toString()
                cultivationNew.dateCultivation = dateCurrent
                dataBase?.cultivationDao()?.addCultivation(cultivationNew)
                (activity as MainFarmActivity).handleReplaceFragment(
                    TreeRecyclerFragment.newInstance(),
                    parent = R.id.containerMain
                )
                dialog?.dismiss()
            }
        }
    }

    private fun initData() {
        listPlants = dataBase?.plantDao()?.getAllPlant()
        val arrayNamePlants = arrayListOf<String>()
        listPlants?.forEach { plant ->
            plant.name?.let { name -> arrayNamePlants.add(name) }
        }
        val adapterSpinner =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayNamePlants)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTree?.adapter = adapterSpinner
        spinnerTree?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                setOnSelectPlantFromSpinner(position)
            }
        }
    }

    private fun setOnSelectPlantFromSpinner(position: Int) {
        listPlants?.get(position)?.let {
            var text = getString(R.string.w7_text_grow_zone, it.growZoneNumber)
            text += getString(R.string.w7_text_watering, it.wateringInterval)
            tvInformationDialog?.text = text
            imgDialogPlant?.setImageURI(Uri.parse(it.imageUri))
            plantSelected = it
        }
    }
}
