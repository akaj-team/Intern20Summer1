package com.asiantech.intern20summer1.w7.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.R.drawable.img_farm_logo
import com.asiantech.intern20summer1.w7.main.recyclerview.RecyclerAdapter
import com.asiantech.intern20summer1.w7.main.recyclerview.RecyclerItem
import kotlinx.android.synthetic.`at-huybui`.fragment_farm_vegetable.*

class VegetableFragment : Fragment() {

    companion object {
        internal fun newInstance() = VegetableFragment()
    }

    private val vegetableList: MutableList<RecyclerItem> = mutableListOf()
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
    }

    private fun initAdapter() {
        recyclerViewVegetable.adapter = adapterRecycler
        recyclerViewVegetable.layoutManager = LinearLayoutManager(context)
        recyclerViewVegetable.setHasFixedSize(true)
    }

    private fun initData() {
        vegetableList.add(RecyclerItem("xxxx", "xxxxxxxxxxxxxxx", img_farm_logo))
        vegetableList.add(RecyclerItem("2222", "xxxxxxxxxxxxxxx", img_farm_logo))
        vegetableList.add(RecyclerItem("3333", "xxxxxxxxxxxxxxx", img_farm_logo))
        vegetableList.add(RecyclerItem("4444", "xxxxxxxxxxxxxxx", img_farm_logo))
        vegetableList.add(RecyclerItem("5555", "xxxxxxxxxxxxxxx", img_farm_logo))
        vegetableList.add(RecyclerItem("6666", "xxxxxxxxxxxxxxx", img_farm_logo))
        vegetableList.add(RecyclerItem("7777", "xxxxxxxxxxxxxxx", img_farm_logo))
        vegetableList.add(RecyclerItem("8888", "xxxxxxxxxxxxxxx", img_farm_logo))
        vegetableList.add(RecyclerItem("9999", "xxxxxxxxxxxxxxx", img_farm_logo))
        vegetableList.add(RecyclerItem("yyyy", "xxxxxxxxxxxxxxx", img_farm_logo))
    }
}
