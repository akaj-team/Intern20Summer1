package com.asiantech.intern20summer1.week7.fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.data.AppDatabase
import com.asiantech.intern20summer1.week7.extensions.PlantStatus
import com.asiantech.intern20summer1.week7.models.Cultivation
import com.asiantech.intern20summer1.week7.models.Plant
import kotlinx.android.synthetic.`at-linhle`.fragment_plant_detail.*

class PlantDetailFragment : Fragment() {
    companion object {
        private const val POSITION_KEY = "position"
        internal fun newInstance(id: Int?) = PlantDetailFragment().apply {
            arguments = Bundle().apply {
                id?.let { putInt(POSITION_KEY, it) }
            }
        }
    }

    private var appDatabase: AppDatabase? = null
    private var cultivation: Cultivation? = null
    private var plant: Plant? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appDatabase = AppDatabase.getInstance(requireContext())
        return inflater.inflate(R.layout.fragment_plant_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        handleArrowBackClicked()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        cultivation =
            appDatabase?.getCultivationDao()?.getCultivation(arguments?.getInt(POSITION_KEY))
        plant = appDatabase?.getPlantDao()?.getPlant(cultivation?.plantId)
        if (PlantStatus().isWormed(plant, cultivation)
            && !PlantStatus().isComingHarvest(plant, cultivation)
        ) {
            tvWormedPlantDetail?.text = getString(R.string.plant_detail_fragment_worm_description)
        }
        if (PlantStatus().isLackedWater(plant, cultivation)
            && !PlantStatus().isComingHarvest(plant, cultivation)
        ) {
            tvLackedWaterPlantDetail?.text =
                getString(R.string.plant_detail_fragment_lack_water_description)
        }
        if (PlantStatus().isComingHarvest(plant, cultivation)) {
            tvComingHarvestPlantDetail?.text =
                getString(R.string.plant_detail_fragment_harvest_description)
        }
        imgCultivationDetail?.setImageURI(Uri.parse(plant?.imageUrl))
        tvGrownDateDetail.text =
            getString(R.string.plant_detail_fragment_grown_date_description) + cultivation?.dateCultivation
        tvWateringIntervalDateDetail.text =
            getString(R.string.plant_detail_fragment_watering_date_description) + cultivation?.dateWatering
        tvPlantDescriptionDetail.text = plant?.description
    }

    private fun handleArrowBackClicked() {
        imgArrowBack?.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }
}
