package com.asiantech.intern20summer1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.VegetableFarmMainActivity
import com.asiantech.intern20summer1.adapter.VegetableRecyclerViewAdapter
import com.asiantech.intern20summer1.data.VegetableItemRecyclerView
import kotlinx.android.synthetic.`at-vuongphan`.w7_recycler_view_fragment.*

class VegetableFragmentRecyclerView : Fragment() {
    private val exampleLists = mutableListOf<VegetableItemRecyclerView>()
    private var adapterRecycler = VegetableRecyclerViewAdapter(exampleLists)

    companion object {
        private const val FINISH_LOOP = 9
        internal fun newInstance(): VegetableFragmentRecyclerView {
            return VegetableFragmentRecyclerView()
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
        handleOnItemClick()
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

    private fun newData(): MutableList<VegetableItemRecyclerView> {
        val pots = mutableListOf<VegetableItemRecyclerView>()
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
        for (i in 0..FINISH_LOOP) {
            pots.add(
                VegetableItemRecyclerView(
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
