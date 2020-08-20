package com.asiantech.intern20summer1.week7

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.R.id.item_vuon_cay
import kotlinx.android.synthetic.`at-hoangtran`.grow_plant_fragment.*

class GrowPlantFragment : Fragment() {
    companion object {
        private const val COUNT_DOWN_INTERVAL = 1000L
        internal fun newInstance() = GrowPlantFragment()
    }

    private var appDatabase: AppDatabase? = null
    private var plantList: MutableList<Cultivation?> = mutableListOf()
    private var adapter = GardenViewHolder(plantList)
    private var itemId = item_vuon_cay

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appDatabase = AppDatabase.getInstance(requireContext())
        return inflater.inflate(R.layout.grow_plant_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initAdapter()
        handlePlantClicked()
        handleDataReload()
    }

    private fun initAdapter() {
        recyclerviewGarden.adapter = adapter
        recyclerviewGarden.layoutManager = LinearLayoutManager(context)
        recyclerviewGarden.setHasFixedSize(true)
    }

    internal fun initData(id: Int = item_vuon_cay) {
        plantList.clear()
        appDatabase?.getCultivationDAO()?.getAllCultivation()?.toCollection(plantList)
        if (plantList.size != 0) {
            when (id) {
                item_vuon_cay -> {
                    adapter.notifyDataSetChanged()
                    itemId = id
                }
                R.id.item_cay_sap_thu_hoach -> {
                    initDataForHarvest()
                    itemId = id
                }
                R.id.item_cay_bi_sau -> {
                    initDataForWorm()
                    itemId = id
                }
                R.id.item_cay_bi_thieu_nuoc -> {
                    initDataForWater()
                    itemId = id
                }
            }
        }
    }

    private fun initDataForWorm() {
        val list = mutableListOf<Cultivation>()
        plantList.forEach { cultivation ->
            appDatabase?.getPlantDAO()?.getPlant(cultivation?.plantId)?.let {
                if (PlantStatus().isWormed(it, cultivation) && !PlantStatus().isHarvest(
                        it,
                        cultivation
                    )
                ) {
                    if (cultivation != null) {
                        list.add(cultivation)
                    }
                }
            }
        }
        plantList.clear()
        list.toCollection(plantList)
        adapter.notifyDataSetChanged()
    }

    private fun initDataForWater() {
        val list = mutableListOf<Cultivation>()
        plantList.forEach { cultivation ->
            appDatabase?.getPlantDAO()?.getPlant(cultivation?.plantId)?.let {
                if (PlantStatus().isWatered(it, cultivation) && !PlantStatus().isHarvest(
                        it,
                        cultivation
                    )
                ) {
                    if (cultivation != null) {
                        list.add(cultivation)
                    }
                }
            }
        }
        plantList.clear()
        list.toCollection(plantList)
        adapter.notifyDataSetChanged()
    }

    private fun initDataForHarvest() {
        val list = mutableListOf<Cultivation>()
        plantList.forEach { cultivation ->
            appDatabase?.getPlantDAO()?.getPlant(cultivation?.plantId)?.let {
                if (PlantStatus().isHarvest(it, cultivation)) {
                    if (cultivation != null) {
                        list.add(cultivation)
                    }
                }
            }
        }
        plantList.clear()
        list.toCollection(plantList)
        adapter.notifyDataSetChanged()
    }

    private fun handlePlantClicked() {
        adapter.onItemClicked = {
            val fragment = PlantDetailFragment.newInstance(it, itemId)
            (activity as GardenActivity).handleReplaceFragment(
                fragment,
                backStack = true,
                parent = R.id.flToolbarContainer
            )
        }
    }

    private fun handleDataReload(){
        object : CountDownTimer(COUNT_DOWN_INTERVAL, COUNT_DOWN_INTERVAL) {
            override fun onFinish() {
                initData(itemId)
                this.start()
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }.start()
    }
}
