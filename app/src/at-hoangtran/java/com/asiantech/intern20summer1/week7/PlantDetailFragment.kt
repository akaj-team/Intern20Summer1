package com.asiantech.intern20summer1.week7

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.PlantDialogFragment.Companion.DATE_FORMAT_STRING
import kotlinx.android.synthetic.`at-hoangtran`.plant_detail_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class PlantDetailFragment : Fragment() {
    companion object {
        private const val POSITION_KEY = "position"
        private const val INIT_DATA_KEY = "init"
        internal fun newInstance(id: Int?, itemId: Int?) = PlantDetailFragment().apply {
            arguments = Bundle().apply {
                id?.let { putInt(POSITION_KEY, it) }
                itemId?.let { putInt(INIT_DATA_KEY, it) }
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
        return inflater.inflate(R.layout.plant_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        handleArrowClick()
        handlePlantCutButton()
        handleWateringButton()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        cultivation =
            arguments?.getInt(POSITION_KEY)?.let {
                appDatabase?.getCultivationDAO()?.getCultivation(
                    it
                )
            }
        plant = appDatabase?.getPlantDAO()?.getPlant(cultivation?.plantId)
        if (PlantStatus().isWormed(plant, cultivation) && !PlantStatus().isHarvest(
                plant,
                cultivation
            )
        ) {
            tvWormedPlantDetail?.text = getString(R.string.item_cay_bi_sau)
        }
        if (PlantStatus().isWatered(plant, cultivation) && !PlantStatus().isHarvest(
                plant,
                cultivation
            )
        ) {
            tvLackedWaterPlantDetail?.text = getString(R.string.item_cay_bi_thieu_nuoc)
        }
        if (PlantStatus().isHarvest(plant, cultivation)) {
            tvComingHarvestPlantDetail?.text = getString(R.string.item_cay_sap_thu_hoach)
        }
        imgCultivationDetail?.setImageURI(Uri.parse(plant?.imageUrl))
        tvGrownDateDetail.text =
            getString(R.string.plant_detail_fragment_grown_date_description) + cultivation?.dateCultivation
        tvWateringIntervalDateDetail.text =
            getString(R.string.plant_detail_fragment_watering_date_description) + cultivation?.dateWatering
        tvPlantDescriptionDetail.text = plant?.description
    }

    private fun handleArrowClick() {
        imgArrowBack.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun handleWateringButton() {
        btnWatering?.setOnClickListener {
            cultivation?.let {
                val dateFormat = SimpleDateFormat(DATE_FORMAT_STRING)
                appDatabase?.getCultivationDAO()
                    ?.updateWateringDate(it.id, dateFormat.format(Date()))
                initView()
                tvWormedPlantDetail.text = ""
                tvLackedWaterPlantDetail.text = ""
            }
        }
    }

    private fun handlePlantCutButton() {
        btnCutDown?.setOnClickListener {
            cultivation?.let {
                appDatabase?.getCultivationDAO()?.deleteCultivation(it)
            }
            (activity as GardenActivity).apply {
                arguments?.getInt(INIT_DATA_KEY)?.let { id ->
                    GrowPlantFragment.newInstance().initData(id)
                }
            }
            fragmentManager?.popBackStack()
        }
    }
}
