package com.asiantech.intern20summer1.w7.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.database.ConnectDataBase
import com.asiantech.intern20summer1.w7.model.PlantModel
import kotlinx.android.synthetic.`at-huybui`.fragment_dialog.*

open class DialogFragmentFarm : DialogFragment() {

    companion object {
        internal fun newInstance() = DialogFragmentFarm()
    }

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
    }

    private fun handleListenerForButtonBack() {
        imgBackIconDialog.setOnClickListener {
            dialog?.dismiss()
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
        Toast.makeText(context, listPlants?.get(position)?.name, Toast.LENGTH_SHORT).show()
    }
}
