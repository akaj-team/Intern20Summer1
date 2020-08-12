package com.asiantech.intern20summer1.week7.fragments

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
import com.asiantech.intern20summer1.week7.data.AppDatabase
import com.asiantech.intern20summer1.week7.models.Plant
import kotlinx.android.synthetic.`at-linhle`.fragment_dialog.*

open class DialogFragment : DialogFragment() {
    private var plants: List<Plant>? = null
    private var appDatabase: AppDatabase? = null

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
        initView()
        initData()
    }

    private fun handleListenerForButtonBack() {
        imgCancel.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun initView() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        handleListenerForButtonBack()
    }

    private fun initData() {
        plants = appDatabase?.getPlantDao()?.getPlants()
        val listPlants = arrayListOf<String>()
        plants?.forEach { plant ->
            plant.name?.let { name ->
                listPlants.add(name)
            }
        }

        val adapterSpinner =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listPlants)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adapterSpinner
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                onPlantSelected(position)
            }
        }
    }

    private fun onPlantSelected(position: Int) {
        plants?.get(position)?.let { plant ->
            val text =
                "Grow Zone Number: ${plant.growZoneNumber}\nWatering Interval: ${plant.wateringInterval}"
            tvDialogPlantDetail?.text = text
            imgDialogPlant.setImageURI(Uri.parse(plant.imageUrl))
        }
    }
}
