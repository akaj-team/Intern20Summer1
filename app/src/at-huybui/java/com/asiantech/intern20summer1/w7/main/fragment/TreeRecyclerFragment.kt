package com.asiantech.intern20summer1.w7.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.R.drawable.img_farm_logo
import com.asiantech.intern20summer1.w7.main.MainFarmActivity
import com.asiantech.intern20summer1.w7.main.recyclerview.RecyclerAdapter
import com.asiantech.intern20summer1.w7.model.TreeClass
import kotlinx.android.synthetic.`at-huybui`.fragment_farm_vegetable.*

class TreeRecyclerFragment : Fragment() {

    companion object {
        internal fun newInstance() =
            TreeRecyclerFragment()
    }

    private val vegetableList: MutableList<TreeClass> = mutableListOf()
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
        vegetableList.add(
            TreeClass(
                name = "Củ cải ",
                statusWorm = true,
                dateCultivation = "15/9/2000",
                dateHarvest = "20/10/1999",
                image = img_farm_logo
            )
        )
        vegetableList.add(
            TreeClass(
                name = "Củ kieu ",
                statusWorm = true,
                dateCultivation = "15/9/2000",
                dateHarvest = "20/10/1999",
                image = img_farm_logo
            )
        )
        vegetableList.add(
            TreeClass(
                name = "Củ cac ",
                statusWorm = true,
                dateCultivation = "15/9/2000",
                dateHarvest = "20/10/1999",
                image = img_farm_logo
            )
        )
        vegetableList.add(
            TreeClass(
                name = "Củ cu ",
                statusWorm = true,
                dateCultivation = "15/9/2000",
                dateHarvest = "20/10/1999",
                image = img_farm_logo
            )
        )
        vegetableList.add(
            TreeClass(
                name = "Củ cut ",
                statusWorm = true,
                dateCultivation = "15/9/2000",
                dateHarvest = "20/10/1999",
                image = img_farm_logo
            )
        )

    }
}
