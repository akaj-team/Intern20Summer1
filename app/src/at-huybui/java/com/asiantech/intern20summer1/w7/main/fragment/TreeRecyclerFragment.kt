package com.asiantech.intern20summer1.w7.main.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.database.ConnectDataBase
import com.asiantech.intern20summer1.w7.main.MainFarmActivity
import com.asiantech.intern20summer1.w7.main.adapter.RecyclerAdapter
import com.asiantech.intern20summer1.w7.model.PlantModel
import kotlinx.android.synthetic.`at-huybui`.fragment_farm_vegetable.*

class TreeRecyclerFragment : Fragment() {

    companion object {
        internal fun newInstance() =
            TreeRecyclerFragment()
    }

    private var vegetableList: MutableList<PlantModel> = mutableListOf()
    private val adapterRecycler = RecyclerAdapter(vegetableList)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_farm_vegetable, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initAdapter()
        handleOnItemClick()
    }

    private fun initAdapter() {
        recyclerViewVegetable.adapter = adapterRecycler
        recyclerViewVegetable.layoutManager = LinearLayoutManager(context)
        recyclerViewVegetable.setHasFixedSize(true)
    }

    private fun handleOnItemClick() {
        adapterRecycler.onItemClicked = { position ->
            val fragment = TreeInformationFragment.newInstance()
            (activity as MainFarmActivity).handleReplaceFragment(
                fragment,
                true,
                parent = R.id.relativeLayoutContainerMain
            )
        }
    }

    private fun initData() {
        val database: ConnectDataBase? = ConnectDataBase.dataBaseConnect(requireContext())
        val plants = database?.plantDao()?.getAllPlant()
        Handler().postDelayed({
            plants?.let { plant ->
                d("XXXX", "load data")
                plant.forEach {
                    vegetableList.add(it)
                }
            }
            adapterRecycler.notifyDataSetChanged()
        }, 2000)
    }
}
