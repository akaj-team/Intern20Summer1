package com.asiantech.intern20summer1.week7

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.activity_vegetable_garden.*
import kotlinx.android.synthetic.`at-hoangtran`.plant_dialog_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class PlantDialogFragment : DialogFragment() {
    companion object {
        internal val DATE_FORMAT_STRING = "dd//MM//yyyy HH:mm"
    }

    private var plants: List<Plant>? = null
    private var appDatabase: AppDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.plant_dialog_fragment, fl_container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initView()
    }

    private fun initView() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        handleBackButton()
    }

    private fun handleBackButton() {
        imgX.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun initData() {
        plants = appDatabase?.getPlantDAO()?.getPlants()
        val listPlant = arrayListOf<String>()
        plants?.forEach{plant ->
            plant.name?.let {name ->
                listPlant.add(name)
            }
        }
        val adapterSpinner =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listPlant)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDialog?.adapter = adapterSpinner
        spinnerDialog?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                onPlantSelected(position)
                handleOKButton(position)
            }
        }
    }

    fun onPlantSelected(position: Int) {
        plants?.get(position).let { plant ->
            val text =
                "Grow Zome Number: ${plant?.growZoneNumber}\n Watering Interval: ${plant?.wateringInterval}"
            tvDialogDetail?.text = text
            imgDialogPlant.setImageURI(Uri.parse(plant?.imageUrl))
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun handleOKButton(position: Int) {
        val dateFormat = SimpleDateFormat(DATE_FORMAT_STRING)
        val dateCurrent = dateFormat.format(Date())
        val user = appDatabase?.getUserDAO()?.getUser()
        val cultivation = Cultivation()
        btnOK?.setOnClickListener {
            Cultivation().apply {
                userGrowId = user?.userId
                plantId = plants?.get(position)?.id
                dateCultivation = dateCurrent
                dateWatering = dateCurrent
            }
        }
    }
}
