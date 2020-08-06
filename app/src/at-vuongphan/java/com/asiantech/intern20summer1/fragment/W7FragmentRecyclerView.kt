package com.asiantech.intern20summer1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.adapter.W7RecyclerViewAdapter
import com.asiantech.intern20summer1.data.W7ItemRecyclerView
import kotlinx.android.synthetic.`at-vuongphan`.w7_recycler_view_fragment.*

class W7FragmentRecyclerView : Fragment() {
    private val exampleLists = mutableListOf<W7ItemRecyclerView>()
    private var adapterRecycler = W7RecyclerViewAdapter(exampleLists)

    companion object {
        internal fun newInstance(): W7FragmentRecyclerView {
            return W7FragmentRecyclerView()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w7_recycler_view_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        exampleLists.addAll(newData())
    }

    private fun initAdapter() {
        recyclerView.adapter = adapterRecycler
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
    }

    private fun newData(): MutableList<W7ItemRecyclerView> {
        val pots = mutableListOf<W7ItemRecyclerView>()
        val timerHarvest = listOf(
            R.string.timer_tow,
            R.string.timer_tow_two
        )
        val timerGrow = listOf(
            R.string.timer_one,
            R.string.timer_one_one
        )
        val nameVegetable = listOf(
            R.string.vegetable_one,
            R.string.vegetable_two, R.string.vegetable_three
        )
        for (i in 0..9) {
            pots.add(
                W7ItemRecyclerView(
                    resources.getString(nameVegetable.random()),
                    R.drawable.ic_splash,
                    resources.getString(timerGrow.random()),
                    resources.getString(timerHarvest.random()),
                    R.drawable.ic_splash
                )
            )
        }
        return pots
    }

}