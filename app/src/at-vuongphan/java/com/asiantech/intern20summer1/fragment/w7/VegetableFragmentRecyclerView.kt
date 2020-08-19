package com.asiantech.intern20summer1.fragment.w7

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.w7.VegetableFarmMainActivity
import com.asiantech.intern20summer1.adapter.w7.VegetableRecyclerViewAdapter
import com.asiantech.intern20summer1.database.Cultivation
import com.asiantech.intern20summer1.database.VegetableDB
import com.asiantech.intern20summer1.extension.w7.PlantStatus
import kotlinx.android.synthetic.`at-vuongphan`.w7_activity_main_farm.*
import kotlinx.android.synthetic.`at-vuongphan`.w7_recycler_view_fragment.*

class VegetableFragmentRecyclerView : Fragment() {
    internal var id = 1
    private var dataBase: VegetableDB? = null
    private var vegetableList: MutableList<Cultivation> = mutableListOf()
    private val adapterRecycler =
        VegetableRecyclerViewAdapter(
            vegetableList
        )

    companion object {
        private const val MINUTES = 60
        private const val HOURS = 3600
        private const val TIMER_REFRESH = 2000L
        internal const val ID_GARDEN = 1
        internal const val ID_HARVEST = 2
        internal const val ID_WORM = 3
        internal const val ID_WATER = 4
        private const val DIVIDE_NUMBER = 4
        internal fun newInstance(): VegetableFragmentRecyclerView {
            return VegetableFragmentRecyclerView()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBase = VegetableDB.dataBaseConnect(requireContext())
        return inflater.inflate(R.layout.w7_recycler_view_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        handleOnItemClick()
        replaceData()
        initItemDrawer()
    }

    private fun initAdapter() {
        recyclerView.adapter = adapterRecycler
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
    }


    private fun handleOnItemClick() {
        adapterRecycler.onItemClicked = { id ->
            navigationView?.visibility = View.INVISIBLE
            val fragment = VegetableDetailFragment.newInstance(id)
            (activity as VegetableFarmMainActivity).handleReplaceFragment(
                fragment,
                true,
                parent = R.id.rlContainer
            )
        }
    }

    private fun initItemDrawer() {
        (activity as? VegetableFarmMainActivity)?.onClickMenu = {
            id = it
            initData(id)
        }
    }

    private fun replaceData() {
        object : CountDownTimer(
            TIMER_REFRESH,
            TIMER_REFRESH
        ) {
            override fun onFinish() {
                initData(id)
                this.start()
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }.start()
    }

    internal fun initData(id: Int = 1) {
        vegetableList.clear()
        dataBase?.cultivationDao()?.getAllCultivation()?.let {
            it.toCollection(vegetableList)
            tvNotPlant?.visibility = View.INVISIBLE
        }
        if (vegetableList.isNullOrEmpty()) {
            tvNotPlant?.apply {
                visibility = View.VISIBLE
                text = getString(R.string.text_view_recycler)
            }
        } else {
            when (id) {
                ID_GARDEN -> {
                    adapterRecycler.notifyDataSetChanged()
                }
                ID_HARVEST -> {
                    initDataHarvest()
                }
                ID_WORM -> {
                    initDataWorm()
                }
                ID_WATER -> {
                    initDataLackWater()
                }
            }
        }
    }

    private fun initDataWorm() {
        val list = mutableListOf<Cultivation>()
        vegetableList.forEach { it ->
            dataBase?.plantDao()?.getPlant(it.plantId)?.let { it1 ->
                if (PlantStatus().isPlantWorm(it1, it) && !PlantStatus().isPlantHarvest(it1, it)) {
                    list.add(it)
                }
            }
        }
        vegetableList.clear()
        list.toCollection(vegetableList)
        if (vegetableList.isNullOrEmpty()) {
            tvNotPlant?.apply {
                visibility = View.VISIBLE
                text = resources.getString(R.string.text_view_recycler_worm)
            }
        }
        adapterRecycler.notifyDataSetChanged()
    }

    private fun initDataHarvest() {
        val list = mutableListOf<Cultivation>()
        vegetableList.forEach { it ->
            dataBase?.plantDao()?.getPlant(it.plantId)?.let { it1 ->
                if (PlantStatus().isPlantHarvest(it1, it)) {
                    list.add(it)
                }
            }
        }
        vegetableList.clear()
        list.toCollection(vegetableList)
        if (vegetableList.isNullOrEmpty()) {
            tvNotPlant?.apply {
                visibility = View.VISIBLE
                text = resources.getString(R.string.text_view_recycler_harvest)
            }
        }
        adapterRecycler.notifyDataSetChanged()
    }

    private fun initDataLackWater() {
        val list = mutableListOf<Cultivation>()
        vegetableList.forEach { it ->
            dataBase?.plantDao()?.getPlant(it.plantId)?.let { it1 ->
                if (PlantStatus().isPlantLackWater(it1, it) && !PlantStatus().isPlantHarvest(
                        it1,
                        it
                    )
                ) {
                    list.add(it)
                }
            }
        }
        vegetableList.clear()
        list.toCollection(vegetableList)
        if (vegetableList.isNullOrEmpty()) {
            tvNotPlant?.apply {
                visibility = View.VISIBLE
                text = resources.getString(R.string.text_view_recycler_water)
            }
        }
        adapterRecycler.notifyDataSetChanged()
    }
}
