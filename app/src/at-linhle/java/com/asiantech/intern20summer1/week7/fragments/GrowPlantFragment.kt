package com.asiantech.intern20summer1.week7.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.adapters.VegetableViewHolder
import com.asiantech.intern20summer1.week7.data.AppDatabase
import com.asiantech.intern20summer1.week7.models.Cultivation
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
    }

    private fun initAdapter() {
        recyclerViewVegetable.adapter = adapter
        recyclerViewVegetable.layoutManager = LinearLayoutManager(context)
        recyclerViewVegetable.setHasFixedSize(true)
    }

    private fun initData() {
        plantItemList.clear()
        appDatabase?.getCultivation()?.getAllCultivation()?.toCollection(plantItemList)

        if (plantItemList.size != 0) {
            adapter.notifyDataSetChanged()
        }
    }
}
