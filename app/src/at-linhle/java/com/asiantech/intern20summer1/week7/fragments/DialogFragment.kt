package com.asiantech.intern20summer1.week7.fragments

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
import com.asiantech.intern20summer1.week7.data.AppDatabase
import com.asiantech.intern20summer1.week7.models.Plant
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-linhle`.fragment_dialog.*
import kotlinx.android.synthetic.`at-linhle`.fragment_tab_layout.*

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
        if(plants?.size == 1){
            Toast.makeText(activity,"alalalla",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(activity,"babababbab",Toast.LENGTH_SHORT).show()
        }
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
            var text = "Grow Zone: " + plant.growZoneNumber
            text += "\nWatering: ${plant.wateringInterval}"
            tvDialogPlantDetail?.text = text
            Glide.with(this).load(plant.imageUrl).into(imgDialogPlant)
        }
    }
}
