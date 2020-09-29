package com.asiantech.intern20summer1.week7

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.plant_dialog_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class PlantDialogFragment : DialogFragment() {
    companion object {
        internal const val DATE_FORMAT_STRING = "dd/MM/yyyy HH:mm"
    }

    private var plants: List<Plant>? = null
    private var appDatabase: AppDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.plant_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appDatabase = AppDatabase.getInstance(requireContext())
        handleBackButton()
        initView()
        initData()
    }

    private fun initView() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun handleBackButton() {
        imgX.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun initData() {
        plants = appDatabase?.getPlantDAO()?.getPlants()
        Log.d("TAG11", "initData: ${plants?.size}")
        val listPlant = arrayListOf<String>()
        plants?.forEach { plant ->
            plant.name?.let { name ->
                listPlant.add(name)
            }
        }
        spinnerDialog?.setItems(listPlant)
        spinnerDialog?.setOnItemSelectedListener { _, position, _, _ ->
            onPlantSelected(position)
            handleOKButton(position)
        }
    }

    private fun onPlantSelected(position: Int) {
        plants?.get(position).let { plant ->
            val text =
                "Grow Zome Number: ${plant?.growZoneNumber}\n Watering Interval: ${plant?.wateringInterval}"
            tvDialogDetail?.text = text
            Log.d("TAG11", "handleOKButton: ${plant?.imageUrl}")
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
            cultivation.apply {
                userGrowId = user?.userId
                plantId = plants?.get(position)?.plantId
                dateCultivation = dateCurrent
                dateWatering = dateCurrent
            }
            appDatabase?.getCultivationDAO()?.insertCultivation(cultivation)
            (activity as GardenActivity).apply {
                GrowPlantFragment.newInstance().initData()
            }
            dialog?.dismiss()
        }
    }
}
