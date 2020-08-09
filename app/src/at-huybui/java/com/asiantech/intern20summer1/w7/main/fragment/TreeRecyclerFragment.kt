package com.asiantech.intern20summer1.w7.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.database.ConnectDataBase
import com.asiantech.intern20summer1.w7.main.MainFarmActivity
import com.asiantech.intern20summer1.w7.main.adapter.RecyclerAdapter
import com.asiantech.intern20summer1.w7.model.CultivationModel
import kotlinx.android.synthetic.`at-huybui`.fragment_farm_vegetable.*

class TreeRecyclerFragment : Fragment() {

    companion object {
        internal fun newInstance() = TreeRecyclerFragment()
    }

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

    private fun initData() {
        vegetableList.clear()
        dataBase?.cultivationDao()?.getAllCultivation()?.let {list->
            list.forEach {
                vegetableList.add(it)
                tvNotPlant?.visibility = View.INVISIBLE
            }

            if (vegetableList.isNullOrEmpty()) {
                tvNotPlant?.apply {
                    visibility = View.VISIBLE
                    text = getString(R.string.w7_chua_co_cay_nao_duoc_trong)
                }
            }
            adapterRecycler.notifyDataSetChanged()
        }

        tvNotPlant?.setOnClickListener {
            (activity as MainFarmActivity).handleShowDialogFragment()
        }
    }
}
