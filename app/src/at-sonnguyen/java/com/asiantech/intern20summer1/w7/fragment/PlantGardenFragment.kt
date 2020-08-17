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
import com.asiantech.intern20summer1.w7.extension.replaceFragment
import kotlinx.android.synthetic.`at-sonnguyen`.w7_fragment_vegetable_garden.*

class PlantGardenFragment : Fragment() {
    private var database: PlantDatabase? = null
    private var cultivationList: MutableList<Cultivation> = mutableListOf()
    private var cultivationAdapter = PlantAdapter(cultivationList)

    companion object {
        internal fun newInstance() = PlantGardenFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        database = PlantDatabase.getInstance(requireContext())
        return inflater.inflate(R.layout.w7_fragment_vegetable_garden, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
        handleOnItemClickListener()
    }
    private fun initAdapter(){
        recyclerViewHome.adapter = cultivationAdapter
        recyclerViewHome.layoutManager = LinearLayoutManager(context)
        recyclerViewHome.setHasFixedSize(true)
    }
    internal fun initData(){
        database?.cultivationDao()?.getAllCultivation()?.let {list ->
            cultivationList.clear()
            list.toCollection(cultivationList)
            if(cultivationList.isNullOrEmpty()){
                tvNoPlant.text = getString(R.string.w7_no_plant_text_view_text)
            }
            cultivationAdapter.notifyDataSetChanged()
        }
    }

    private fun handleOnItemClickListener(){
        cultivationAdapter.onItemClick = {id ->
            val fragment = PlantDetailFragment.newInstance(id)
            (activity as HomeActivity)?.replaceFragment(R.id.flContent,fragment,true)
        }
    }
}
