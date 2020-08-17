package com.asiantech.intern20summer1.w7.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.activity.HomeActivity
import com.asiantech.intern20summer1.w7.adapter.PlantAdapter
import com.asiantech.intern20summer1.w7.database.PlantDatabase
import com.asiantech.intern20summer1.w7.database.data.Cultivation
import com.asiantech.intern20summer1.w7.extension.isWormed
import com.asiantech.intern20summer1.w7.extension.replaceFragment
import kotlinx.android.synthetic.`at-sonnguyen`.w7_fragment_vegetable_garden.*

class WormedPlantFragment : Fragment() {
    companion object {
        internal fun newInstance() = WormedPlantFragment()
    }

    private var database: PlantDatabase? = null
    private var plantList: MutableList<Cultivation> = mutableListOf()
    private var adapter = PlantAdapter(plantList)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        database = PlantDatabase.getInstance(requireContext())
        return inflater.inflate(R.layout.w7_fragment_vegetable_garden, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initData()
        handleOnItemClickListener()
    }

    private fun initAdapter() {
        recyclerViewHome.adapter = adapter
        recyclerViewHome.layoutManager = LinearLayoutManager(context)
        recyclerViewHome.setHasFixedSize(true)
    }

    internal fun initData() {
        database?.cultivationDao()?.getAllCultivation()?.let { list ->
            plantList.clear()
            list.forEach { cultivation ->
                database?.plantDao()?.getPlant(cultivation.plantId)?.let { plant ->
                    if (isWormed(plant, cultivation)) {
                        plantList.add(cultivation)
                    }
                }
            }
        }
        if (plantList.isNullOrEmpty()){
            tvNoPlant.text = getString(R.string.w7_no_plant_is_wormed)
        }
        adapter.notifyDataSetChanged()
    }

    private fun handleOnItemClickListener() {
        adapter.onItemClick = { id ->
            val fragment = PlantDetailFragment.newInstance(id)
            (activity as HomeActivity)?.replaceFragment(R.id.flContent, fragment, true)
        }
    }
}
