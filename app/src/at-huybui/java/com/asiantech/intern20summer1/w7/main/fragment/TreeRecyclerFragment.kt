package com.asiantech.intern20summer1.w7.main.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.companion.App
import com.asiantech.intern20summer1.w7.database.ConnectDataBase
import com.asiantech.intern20summer1.w7.main.activity.MainFarmActivity
import com.asiantech.intern20summer1.w7.main.adapter.RecyclerAdapter
import com.asiantech.intern20summer1.w7.model.CultivationModel
import kotlinx.android.synthetic.`at-huybui`.fragment_farm_vegetable.*

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/04/20
 * This is fragment class for recycler fragment
 */

class TreeRecyclerFragment : Fragment() {

    companion object {
        private const val TIMER_REFRESH = 5000L
        internal fun newInstance() = TreeRecyclerFragment()
    }

    internal var mode = 1
    private var dataBase: ConnectDataBase? = null
    private var vegetableList: MutableList<CultivationModel> = mutableListOf()
    private val adapterRecycler = RecyclerAdapter(vegetableList)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBase = ConnectDataBase.dataBaseConnect(requireContext())
        return inflater.inflate(R.layout.fragment_farm_vegetable, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initAdapter()
        handleOnItemClick()
        replaceData()
        handleReceiveEventOnclickItemDrawer()
    }

    private fun initAdapter() {
        recyclerViewVegetable.adapter = adapterRecycler
        recyclerViewVegetable.layoutManager = LinearLayoutManager(context)
        recyclerViewVegetable.setHasFixedSize(true)
    }

    private fun handleOnItemClick() {
        adapterRecycler.onItemClicked = { id ->
            val fragment = PlantDetailFragment.newInstance(id)
            (activity as MainFarmActivity).handleReplaceFragment(
                fragment,
                backStack = true,
                parent = R.id.relativeLayoutContainerMain
            )
        }
    }

    private fun replaceData() {
        object : CountDownTimer(TIMER_REFRESH, TIMER_REFRESH) {
            override fun onFinish() {
                initData(mode)
                this.start()
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }.start()
    }

    private fun handleReceiveEventOnclickItemDrawer() {
        (activity as MainFarmActivity).onClickPlants = {
            mode = it
            initData(mode)
        }
        (activity as MainFarmActivity).onClickPlantHarvest = {
            mode = it
            initData(mode)
        }
        (activity as MainFarmActivity).onClickPlantWormed = {
            mode = it
            initData(mode)
        }
        (activity as MainFarmActivity).onClickLackWater = {
            mode = it
            initData(mode)
        }
    }

    internal fun initData(mode: Int = 1) {
        vegetableList.clear()
        dataBase?.cultivationDao()?.getAllCultivation()?.let { list ->
            list.toCollection(vegetableList)
            tvNotPlant?.visibility = View.INVISIBLE
        }

        if (vegetableList.isNullOrEmpty()) {
            tvNotPlant?.apply {
                visibility = View.VISIBLE
                text = getString(R.string.w7_chua_co_cay_nao_duoc_trong)
            }
        } else {
            when (mode) {
                App.MODE_PLANTS -> {
                    adapterRecycler.notifyDataSetChanged()
                }
                App.MODE_WORMED -> {
                    val list = mutableListOf<CultivationModel>()
                    vegetableList.forEach { culti ->
                        dataBase?.plantDao()?.getPlant(culti.plantId)?.let { plant ->
                            if (App().isPlantWormed(plant, culti)) {
                                list.add(culti)
                            }
                        }
                    }
                    vegetableList.clear()
                    list.toCollection(vegetableList)
                    adapterRecycler.notifyDataSetChanged()
                }
                App.MODE_HARVEST -> {
                    val list = mutableListOf<CultivationModel>()
                    vegetableList.forEach { culti ->
                        dataBase?.plantDao()?.getPlant(culti.plantId)?.let { plant ->
                            if (App().isPlantHarvest(plant, culti)) {
                                list.add(culti)
                            }
                        }
                    }
                    vegetableList.clear()
                    list.toCollection(vegetableList)
                    adapterRecycler.notifyDataSetChanged()
                }
                App.MODE_WATERING -> {
                    val list = mutableListOf<CultivationModel>()
                    vegetableList.forEach { culti ->
                        dataBase?.plantDao()?.getPlant(culti.plantId)?.let { plant ->
                            if (App().isPlantLackWater(plant, culti)) {
                                list.add(culti)
                            }
                        }
                    }
                    vegetableList.clear()
                    list.toCollection(vegetableList)
                    adapterRecycler.notifyDataSetChanged()
                }
            }
        }

        tvNotPlant?.setOnClickListener {
            (activity as MainFarmActivity).handleShowDialogFragment()
        }
    }
}
