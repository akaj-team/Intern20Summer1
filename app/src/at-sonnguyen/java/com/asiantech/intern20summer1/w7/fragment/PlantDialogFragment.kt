package com.asiantech.intern20summer1.w7.fragment

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
import com.asiantech.intern20summer1.w7.database.PlantDatabase
import com.asiantech.intern20summer1.w7.database.data.Cultivation
import com.asiantech.intern20summer1.w7.database.data.Plant
import kotlinx.android.synthetic.`at-sonnguyen`.w7_fragment_dialog.*
import java.text.SimpleDateFormat
import java.util.*


class PlantDialogFragment : DialogFragment() {
    private var plantSelected: Plant? = null
    private var plantList: List<Plant>? = null
    private var database: PlantDatabase? = null

    companion object {
        internal const val FORMAT_CODE_DATE = "dd/MM/yyyy HH:mm"
        fun newInstance() = PlantDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w7_fragment_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = PlantDatabase.getInstance(requireContext())
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    private fun initListener(){
        handleBackImageListener()
        handleButtonListener()
    }
     private fun handleButtonListener(){
         btnDialogFragment.setOnClickListener {
             plantSelected?.let {
                 Cultivation().apply {
                     val dateFormat = SimpleDateFormat(FORMAT_CODE_DATE)
                     val dateCurrent = dateFormat.format(Date())
                     plantId = it.plantId
                     dateCultivation = dateCurrent
                     dateWatering = dateCurrent
                     database?.cultivationDao()?.insertCultivation(this)
                     replaceFragment()
                 }
                 Toast.makeText(requireContext(),"aaaaa",Toast.LENGTH_SHORT).show()
                 dialog?.dismiss()
             }
         }
     }
    private fun handleBackImageListener(){
        imgBackIconDialog?.setOnClickListener{
            dismiss()
        }
    }

    private fun initData() {
        plantList = database?.plantDao()?.getAllPlant()
        var plantNameArray = arrayListOf<String>()
        plantList?.forEach {
            it.name?.let { plantName -> plantNameArray.add(plantName) }
        }
        var spinnerAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, plantNameArray)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPlant?.adapter = spinnerAdapter
        spinnerPlant?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                plantList?.get(position)?.let {
                    plantSelected = it
                }
            }
        }
    }
    private fun replaceFragment(){
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.flContent,VegetableGardenFragment())
        fragmentTransaction?.commit()
    }
}