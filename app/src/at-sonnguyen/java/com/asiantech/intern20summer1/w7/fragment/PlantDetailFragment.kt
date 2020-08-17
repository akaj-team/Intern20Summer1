package com.asiantech.intern20summer1.w7.fragment

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.activity.HomeActivity
import com.asiantech.intern20summer1.w7.database.PlantDatabase
import com.asiantech.intern20summer1.w7.database.data.Cultivation
import com.asiantech.intern20summer1.w7.extension.isComingHarvest
import com.asiantech.intern20summer1.w7.extension.isLackedWater
import com.asiantech.intern20summer1.w7.extension.isWormed
import com.asiantech.intern20summer1.w7.fragment.PlantDialogFragment.Companion.FORMAT_CODE_DATE
import kotlinx.android.synthetic.`at-sonnguyen`.w7_fragment_plant_detail.*
import java.text.SimpleDateFormat
import java.util.*

class PlantDetailFragment : Fragment() {
    private var database: PlantDatabase? = null
    private var cultivation: Cultivation? = null

    companion object {
        private const val POSITION_KEY = "position"
        internal fun newInstance(id: Int?) = PlantDetailFragment().apply {
            arguments = Bundle().apply {
                id?.let { putInt(POSITION_KEY, it) }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        database = PlantDatabase.getInstance(requireContext())
        return inflater.inflate(R.layout.w7_fragment_plant_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        handleBackButtonListener()
        handleSprinkleWaterButtonListener()
        handleCutPlantDownButton()
    }

    private fun initView() {
        cultivation = database?.cultivationDao()?.getCultivation(arguments?.getInt(POSITION_KEY))
        val plant = database?.plantDao()?.getPlant(cultivation?.plantId)
        if (isWormed(plant, cultivation)) {
            tvWormedPlantDetail.text =
                getString(R.string.w7_plant_detail_fragment_wormed_plant_text_view)
        } else{
            tvWormedPlantDetail.text = ""
        }
        if (isLackedWater(plant, cultivation)) {
            tvLackedWaterPlantDetail.text =
                getString(R.string.w7_detail_fragment_lacked_water_text_view_text)
        } else{
            tvLackedWaterPlantDetail.text = ""
        }
        if (isComingHarvest(plant, cultivation)) {
            tvComingHarvestPlantDetail.text =
                getString(R.string.w7_detail_fragment_coming_harvest_plant_text_view_text)
        } else{
            tvComingHarvestPlantDetail.text = ""
        }
        imgCultivationDetail?.setImageURI(Uri.parse(plant?.imageUri))
        tvGrownDateDetail.text =
            getString(R.string.w7_text_cultivation, cultivation?.dateCultivation)
        tvWateringIntervalDateDetail.text =
            getString(R.string.w7_text_watering, cultivation?.dateWatering)
        tvPlantDescriptionDetail.text = plant?.description
    }

    @SuppressLint("SimpleDateFormat")
    private fun handleSprinkleWaterButtonListener() {
        btnSprinkleDetail?.setOnClickListener {
            cultivation?.let {
                val dateFormat = SimpleDateFormat(FORMAT_CODE_DATE)
                database?.cultivationDao()?.updateWateringDate(it.id, dateFormat.format(Date()))
                Toast.makeText(
                    requireContext(),
                    getString(R.string.w7_detail_fragment_sprinkle_button_click_toast),
                    Toast.LENGTH_SHORT
                ).show()
                initView()
                tvWormedPlantDetail.visibility = View.INVISIBLE
                tvLackedWaterPlantDetail.visibility = View.INVISIBLE
            }
        }
    }

    private fun handleCutPlantDownButton() {
        btnCutDownPlantDetail.setOnClickListener {
            cultivation?.let {
                database?.cultivationDao()?.deleteCultivation(it)
            }
            refreshData()
            fragmentManager?.popBackStack()
        }
    }

    private fun handleBackButtonListener() {
        imgBackDetail.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun refreshData() {
        (activity as HomeActivity).apply {
            wormedPlantFragment.initData()
            lackedWaterPlantFragment.initData()
            comingHarvestPlant.initData()
            plantGardenFragment.initData()
        }
    }
}
