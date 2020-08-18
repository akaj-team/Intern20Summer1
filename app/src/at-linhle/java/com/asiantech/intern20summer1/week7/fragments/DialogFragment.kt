package com.asiantech.intern20summer1.week7.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.data.AppDatabase
import com.asiantech.intern20summer1.week7.models.Cultivation
import com.asiantech.intern20summer1.week7.models.Plant
import com.asiantech.intern20summer1.week7.views.HomeActivity
import kotlinx.android.synthetic.`at-linhle`.fragment_dialog.*
import java.text.SimpleDateFormat
import java.util.*

open class DialogFragment : DialogFragment() {

    companion object {
        internal const val DATE_FORMAT_STRING = "dd/MM/yyyy HH:mm"
    }

    private var plants: List<Plant>? = null
    private var appDatabase: AppDatabase? = null
    private val growPlantFragment = GrowPlantFragment.newInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appDatabase = AppDatabase.getInstance(requireContext())
        handleBackButtonClicked()
        initView()
        initData()
    }

    private fun handleBackButtonClicked() {
        imgCancel.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun initView() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun initData() {
        plants = appDatabase?.getPlantDao()?.getPlants()
        val listPlants = arrayListOf<String>()
        plants?.forEach { plant ->
            plant.name?.let { name ->
                listPlants.add(name)
            }
        }

        spinner?.setItems(listPlants)
        spinner?.setOnItemSelectedListener { _, position, _, _ ->
            onPlantSelected(position)
            handleOkButtonClicked(position)
        }
    }

    private fun onPlantSelected(position: Int) {
        plants?.get(position)?.let { plant ->
            val text =
                "${getString(R.string.dialog_fragment_grow_zone_description)}${plant.growZoneNumber}" +
                        "${getString(R.string.dialog_fragment_watering_interval_description)}${plant.wateringInterval}"
            tvDialogPlantDetail?.text = text
            imgDialogPlant.setImageURI(Uri.parse(plant.imageUrl))
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun handleOkButtonClicked(position: Int) {
        val dateFormat = SimpleDateFormat(DATE_FORMAT_STRING)
        val currentDate = dateFormat.format(Date())
        val user = appDatabase?.getUserDao()?.getUsers()
        val cultivation = Cultivation()
        btnOkDialog?.setOnClickListener {
            cultivation.apply {
                userGrowId = user?.userId
                plantId = plants?.get(position)?.plantId
                dateCultivation = currentDate
                dateWatering = currentDate
            }
            appDatabase?.getCultivationDao()?.insertCultivation(cultivation)
            (activity as HomeActivity).apply {
                growPlantFragment.initData()
            }
            dialog?.dismiss()
            Toast.makeText(
                activity,
                getString(R.string.dialog_fragment_add_plant_success),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
