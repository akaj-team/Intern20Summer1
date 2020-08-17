package com.asiantech.intern20summer1.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.VegetableFarmMainActivity
import com.asiantech.intern20summer1.adapter.VegetableRecyclerViewAdapter
import com.asiantech.intern20summer1.database.Cultivation
import com.asiantech.intern20summer1.database.Plant
import com.asiantech.intern20summer1.database.VegetableDB
import com.asiantech.intern20summer1.fragment.VegetableDialogFragment.Companion.FORMAT_CODE_DATE
import kotlinx.android.synthetic.`at-vuongphan`.w7_recycler_view_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class VegetableFragmentRecyclerView : Fragment() {
    internal var id = 1
    private var dataBase: VegetableDB? = null
    private var vegetableList: MutableList<Cultivation> = mutableListOf()
    private val adapterRecycler = VegetableRecyclerViewAdapter(vegetableList)

    companion object {
        private const val MINUTES = 60
        private const val HOURS = 3600
        private const val TIMER_REFRESH = 5000L
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
        adapterRecycler.onItemClicked = { _ ->
            val fragment = VegetableDetailFragment.newInstance()
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
        object : CountDownTimer(TIMER_REFRESH, TIMER_REFRESH) {
            override fun onFinish() {
                initData(id)
                this.start()
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }.start()
    }

    private fun initData(id: Int = 1) {
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
                1 -> {
                    adapterRecycler.notifyDataSetChanged()
                }
                2 -> {
                    initDataHarvest()
                }
                3 -> {
                    initDataWorm()
                }
                4 -> {
                    initDataLackWater()
                }
            }
        }
    }

    private fun initDataWorm() {
        val list = mutableListOf<Cultivation>()
        vegetableList.forEach { it ->
            dataBase?.plantDao()?.getPlant(it.plantId)?.let { it1 ->
                if (isPlantWorm(it1, it)) {
                    list.add(it)
                }
            }
        }
        vegetableList.clear()
        list.toCollection(vegetableList)
        adapterRecycler.notifyDataSetChanged()
    }

    private fun initDataHarvest() {
        val list = mutableListOf<Cultivation>()
        vegetableList.forEach { it ->
            dataBase?.plantDao()?.getPlant(it.plantId)?.let { it1 ->
                if (isPlantHarvest(it1, it)) {
                    list.add(it)
                }
            }
        }
        vegetableList.clear()
        list.toCollection(vegetableList)
        adapterRecycler.notifyDataSetChanged()
    }

    private fun initDataLackWater() {
        val list = mutableListOf<Cultivation>()
        vegetableList.forEach { it ->
            dataBase?.plantDao()?.getPlant(it.plantId)?.let { it1 ->
                if (isPlantLackWater(it1, it)) {
                    list.add(it)
                }
            }
        }
        vegetableList.clear()
        list.toCollection(vegetableList)
        adapterRecycler.notifyDataSetChanged()
    }

    @SuppressLint("SimpleDateFormat")
    private fun isPlantWorm(plant: Plant?, culti: Cultivation?): Boolean {
        culti?.dateWatering?.let { dateWatering ->
            val dateFormat = SimpleDateFormat(FORMAT_CODE_DATE)
            var beforeTime = 0
            dateFormat.parse(dateWatering)?.let { date ->
                Calendar.getInstance().apply {
                    time = date
                    beforeTime = getMinuteInDay(this)
                }
            }
            val now = Calendar.getInstance()
            now.time = Date()
            val current = getMinuteInDay(now)
            plant?.wateringInterval?.let {
                return (current - beforeTime) / 2 >= (it * MINUTES)
            }
        }
        return false
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateHarvest(cultivation: String?, plant: Plant): String {
        cultivation?.let { cul ->
            val dateFormat = SimpleDateFormat(FORMAT_CODE_DATE)
            val calendar = Calendar.getInstance()
            dateFormat.parse(cul)?.let { calendar.time = it }
            plant.growZoneNumber?.let { calendar.add(Calendar.MINUTE, it) }
            return dateFormat.format(calendar.time)
        }
        return "null"
    }

    @SuppressLint("SimpleDateFormat")
    fun isPlantLackWater(plant: Plant?, culti: Cultivation?): Boolean {
        culti?.dateWatering?.let { dateWatering ->
            val dateFormat = SimpleDateFormat(FORMAT_CODE_DATE)
            var beforeTime = 0
            dateFormat.parse(dateWatering)?.let { date ->
                Calendar.getInstance().apply {
                    time = date
                    beforeTime = getMinuteInDay(this)
                }
            }
            val now = Calendar.getInstance()
            now.time = Date()
            val current = getMinuteInDay(now)
            plant?.wateringInterval?.let {
                return (current - beforeTime) >= (it * MINUTES)
            }
        }
        return false
    }

    @SuppressLint("SimpleDateFormat")
    fun isPlantHarvest(plant: Plant?, culti: Cultivation?): Boolean {
        culti?.dateCultivation?.let { dateWatering ->
            val dateFormat = SimpleDateFormat(FORMAT_CODE_DATE)
            var beforeTime = 0
            dateFormat.parse(dateWatering)?.let { date ->
                Calendar.getInstance().apply {
                    time = date
                    beforeTime = getMinuteInDay(this)
                }
            }
            val now = Calendar.getInstance()
            now.time = Date()
            val current = getMinuteInDay(now)
            plant?.growZoneNumber?.let {
                return (current - beforeTime) >= (it * MINUTES)
            }
        }
        return false
    }

    private fun getMinuteInDay(calendar: Calendar): Int {
        return calendar.get(Calendar.HOUR) * HOURS + calendar.get(Calendar.MINUTE) * MINUTES + calendar.get(
            Calendar.SECOND
        )
    }
}
