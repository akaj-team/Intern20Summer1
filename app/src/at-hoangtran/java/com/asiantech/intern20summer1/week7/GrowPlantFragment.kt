package com.asiantech.intern20summer1.week7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.grow_plant_fragment.*

class GrowPlantFragment : Fragment() {
    companion object {
        internal fun newInstance() = GrowPlantFragment()
    }

    private var appDatabase: AppDatabase? = null
    private var plantList: MutableList<Cultivation?> = mutableListOf()
    private var adapter = GardenViewHolder(plantList)

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
        handleOnClickItemDrawer()
        handlePlantClicked()
    }

    private fun initAdapter() {
        recyclerviewGarden.adapter = adapter
        recyclerviewGarden.layoutManager = LinearLayoutManager(context)
        recyclerviewGarden.setHasFixedSize(true)
    }

    private fun initData(id:Int = R.id.item_vuon_cay) {
        plantList.clear()
        appDatabase?.getCultivationDAO()?.getAllCultivation()?.toCollection(plantList)
        if (plantList.size != 0) {
            when (id) {
                R.id.item_vuon_cay -> {
                    adapter.notifyDataSetChanged()
                }
                R.id.item_cay_sap_thu_hoach -> {
                    initDataForHarvest()
                }
                R.id.item_cay_bi_sau -> {
                    initDataForWorm()
                }
                R.id.item_cay_bi_thieu_nuoc -> {
                    initDataForWater()
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
            val fragment = PlantDetailFragment.newInstance(it)
            (activity as GardenActivity).handleReplaceFragment(
                fragment,
                backStack = true,
                parent = R.id.flToolbarContainer
            )
        }
    }

    private fun handleOnClickItemDrawer() {
        (activity as GardenActivity).onClickItemMenuDrawer = {
            initData(it)
        }
    }
}
