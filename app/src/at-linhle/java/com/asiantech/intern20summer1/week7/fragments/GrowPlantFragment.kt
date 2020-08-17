package com.asiantech.intern20summer1.week7.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.adapters.VegetableViewHolder
import com.asiantech.intern20summer1.week7.data.AppDatabase
import com.asiantech.intern20summer1.week7.extensions.PlantStatus
import com.asiantech.intern20summer1.week7.models.Cultivation
import com.asiantech.intern20summer1.week7.views.HomeActivity
import kotlinx.android.synthetic.`at-linhle`.activity_home_toolbar.*
import kotlinx.android.synthetic.`at-linhle`.coordinator_layout.*
import kotlinx.android.synthetic.`at-linhle`.fragment_grow_plant.*

class GrowPlantFragment : Fragment() {

    companion object {
        internal fun newInstance() = GrowPlantFragment()
    }

    private var appDatabase: AppDatabase? = null
    private var plantItemList: MutableList<Cultivation?> = mutableListOf()
    private var adapter = VegetableViewHolder(plantItemList)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appDatabase = AppDatabase.getInstance(requireContext())
        return inflater.inflate(R.layout.fragment_grow_plant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initAdapter()
        handleReceiveEventOnclickItemDrawer()
        handlePlantClicked()
    }

    private fun initAdapter() {
        recyclerViewVegetable.adapter = adapter
        recyclerViewVegetable.layoutManager = LinearLayoutManager(context)
        recyclerViewVegetable.setHasFixedSize(true)
    }

    private fun handleReceiveEventOnclickItemDrawer() {
        (activity as HomeActivity).onClickItemMenuDrawer = {
            initData(it)
        }
    }

    private fun initData(id: Int = R.id.itemHome) {
        plantItemList.clear()
        appDatabase?.getCultivationDao()?.getAllCultivation()?.toCollection(plantItemList)


        if (plantItemList.size != 0) {
            when (id) {
                R.id.itemHome -> {
                    adapter.notifyDataSetChanged()
                }
                R.id.itemAlreadyHarvest -> {
                    initDataForHarvest()
                }
                R.id.itemWormVegetable -> {
                    initDataForWormed()
                }
                R.id.itemLackedWater -> {
                    initDataForLackWater()
                }
            }
        }
    }

    private fun initDataForWormed() {
        val list = mutableListOf<Cultivation>()
        plantItemList.forEach { cultivation ->
            appDatabase?.getPlantDao()?.getPlant(cultivation?.plantId)?.let { plant ->
                if (PlantStatus().isWormed(plant, cultivation) && !PlantStatus().isComingHarvest(
                        plant,
                        cultivation
                    )
                ) {
                    if (cultivation != null) {
                        list.add(cultivation)
                    }
                }
            }
        }
        plantItemList.clear()
        list.toCollection(plantItemList)
        adapter.notifyDataSetChanged()
    }

    private fun initDataForLackWater() {
        val list = mutableListOf<Cultivation>()
        plantItemList.forEach { cultivation ->
            appDatabase?.getPlantDao()?.getPlant(cultivation?.plantId)?.let { plant ->
                if (PlantStatus().isLackedWater(
                        plant,
                        cultivation
                    ) && !PlantStatus().isComingHarvest(plant, cultivation)
                ) {
                    if (cultivation != null) {
                        list.add(cultivation)
                    }
                }
            }
        }
        plantItemList.clear()
        list.toCollection(plantItemList)
        adapter.notifyDataSetChanged()
    }

    private fun initDataForHarvest() {
        val list = mutableListOf<Cultivation>()
        plantItemList.forEach { cultivation ->
            appDatabase?.getPlantDao()?.getPlant(cultivation?.plantId)?.let { plant ->
                if (PlantStatus().isComingHarvest(plant, cultivation)) {
                    if (cultivation != null) {
                        list.add(cultivation)
                    }
                }
            }
        }
        plantItemList.clear()
        list.toCollection(plantItemList)
        adapter.notifyDataSetChanged()
    }

    private fun handlePlantClicked() {
        adapter.onItemClicked = {
            val fragment = PlantDetailFragment.newInstance(it)
            (activity as HomeActivity).handleReplaceFragment(
                fragment,
                backStack = true,
                parent = R.id.rlCoordinatorContainer
            )
        }
    }
}
