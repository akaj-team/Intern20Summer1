package com.asiantech.intern20summer1.w7.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w7.adapter.VegetableAdapter
import com.asiantech.intern20summer1.w7.data.Vegetable
import kotlinx.android.synthetic.`at-sonnguyen`.w7_fragment_vegetable_garden.*

class VegetableGardenFragment : Fragment(){
    private lateinit var adapter: VegetableAdapter
    private var items = mutableListOf<Vegetable>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w7_fragment_vegetable_garden,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initData()
    }
    private fun initAdapter(){
        adapter = VegetableAdapter(items)
        recyclerViewHome.layoutManager = LinearLayoutManager(activity)
        recyclerViewHome.adapter = adapter
    }
    private fun initData(){
        items.addAll(getNewData())
    }

    private fun getNewData(): MutableList<Vegetable> {
        items = mutableListOf<Vegetable>()
        for (i in 0 until 3){
            items.add(Vegetable("cai","15/8","20/8", R.drawable.ic_worm))
        }
        return items
    }
}