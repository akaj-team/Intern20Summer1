package com.asiantech.intern20summer1.w7.main.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.companion.App
import com.asiantech.intern20summer1.w7.companion.CustomSpinner
import com.asiantech.intern20summer1.w7.database.ConnectDataBase
import com.asiantech.intern20summer1.w7.main.activity.MainFarmActivity
import com.asiantech.intern20summer1.w7.model.CultivationModel
import com.asiantech.intern20summer1.w7.model.PlantModel
import kotlinx.android.synthetic.`at-huybui`.w7_fragment_dialog.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/04/20
 * This is fragment class for dialog fragment
 */

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
        return inflater.inflate(R.layout.w7_fragment_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBase = ConnectDataBase.dataBaseConnect(requireContext())

        handleSpinner()
        initView()
        initData()
    }

    private fun initView() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        handleListenerForButtonBack()
        handleListenerForButtonOk()
    }

    private fun handleListenerForButtonBack() {
        imgBackIconDialog.setOnClickListener {
            dialog?.dismiss()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun handleListenerForButtonOk() {
        btnOkDialog?.setOnClickListener {
            plantSelected?.let { plant ->
                CultivationModel().apply {
                    val dateFormat = SimpleDateFormat(App.FORMAT_CODE_DATE)
                    val dateCurrent = dateFormat.format(Date())
                    plantId = plant.plantId
                    dateCultivation = dateCurrent
                    dateWatering = dateCurrent
                    dataBase?.cultivationDao()?.addCultivation(this)
                }
                (activity as MainFarmActivity).apply { fragment.initData(fragment.mode) }
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
        listPlants?.get(position)?.let { plant ->
            var text = getString(R.string.w7_text_grow_zone, plant.growZoneNumber)
            text += "\n${getString(R.string.w7_text_watering, plant.wateringInterval)}"
            tvInformationDialog?.text = text
            imgDialogPlant?.setImageURI(Uri.parse(plant.imageUri))
            plantSelected = plant
        }
    }

    private fun handleSpinner() {
        spinnerTree?.setSpinnerEventsListener(object : CustomSpinner.OnSpinnerEventsListener {
            override fun onSpinnerOpened(spinner: Spinner?) {
                Toast.makeText(requireContext(), "open", Toast.LENGTH_SHORT).show()
                imgIconSpinner?.apply {
                    setBackgroundResource(R.drawable.bg_w7_select_spinner)
                    val rocketAnimation = background as AnimationDrawable
                    rocketAnimation.start()
                }
            }

            override fun onSpinnerClosed(spinner: Spinner?) {
                imgIconSpinner?.apply {
                    setBackgroundResource(R.drawable.bg_w7_animation_off)
                    val rocketAnimation = background as AnimationDrawable
                    rocketAnimation.start()
                }
                Toast.makeText(requireContext(), "close", Toast.LENGTH_SHORT).show()
            }
        })
    }
}