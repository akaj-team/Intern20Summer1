package com.asiantech.intern20summer1.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.activity.VegetableFarmMainActivity
import com.asiantech.intern20summer1.adapter.VegetableRecyclerViewAdapter
import com.asiantech.intern20summer1.database.Plant
import com.asiantech.intern20summer1.database.VegetableDB
import kotlinx.android.synthetic.`at-vuongphan`.w7_recycler_view_fragment.*

class VegetableFragmentRecyclerView : Fragment() {
    internal var mode = 1
    private var dataBase: VegetableDB? = null
    private var vegetableList: MutableList<Plant> = mutableListOf()
    private val adapterRecycler = VegetableRecyclerViewAdapter(vegetableList)

    companion object {
        private const val TIMER_REFRESH = 5000L
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
        dataBase = VegetableDB.dataBaseConnect(requireContext())
        return inflater.inflate(R.layout.w7_recycler_view_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        handleOnItemClick()
        replaceData()
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

    private fun replaceData() {
        object : CountDownTimer(TIMER_REFRESH, TIMER_REFRESH) {
            override fun onFinish() {
                initData(mode)
                this.start()
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }.start()
    }

    private fun initData(mode: Int = 1) {
        vegetableList.clear()
        dataBase?.plantDao()?.getAllPlant()?.let { list ->
            list.toCollection(vegetableList)
        }
        adapterRecycler.notifyDataSetChanged()
    }
}
