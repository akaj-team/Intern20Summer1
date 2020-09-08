package com.asiantech.intern20summer1.w11.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w11.views.Weight
import kotlinx.android.synthetic.`at-huybui`.w11_fragment_graph.*

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 07/09/2020.
 * This is GraphFragment TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */
class GraphFragment : Fragment() {

    companion object {
        internal fun newInstance() = GraphFragment()
    }

    var list = mutableListOf<Weight>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w11_fragment_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        btnChange?.setOnClickListener {
            initData()
            graphView?.run {
                replaceAllData(list)
                verticalMaxValue = 200
                numberWeek = 3
                horizontalAxisWidth = 90f
                verticalAxisWidth = 20f
                graphTitle = "Đồ Thị"
                graphTitleSize = 40f
                marginBottom = 20f
                show()
            }
        }
    }

    private fun initData() {
        list.clear()
        list.add(Weight((50..120).random(), 1))
        list.add(Weight((50..120).random(), 2))
        list.add(Weight((50..120).random(), 3))
        list.add(Weight((50..120).random(), 4))
        list.add(Weight((50..120).random(), 5))
        list.add(Weight((50..120).random(), 6))
        list.add(Weight((50..120).random(), 7))
        list.add(Weight((50..120).random(), 8))
        list.add(Weight((50..120).random(), 9))
        list.add(Weight((50..120).random(), 10))
        list.add(Weight((50..120).random(), 11))
        list.add(Weight((50..120).random(), 12))
        list.add(Weight((50..120).random(), 13))
        list.add(Weight((50..120).random(), 14))
        list.add(Weight((50..120).random(), 15))
        list.add(Weight((50..120).random(), 16))
        list.add(Weight((50..120).random(), 17))
        list.add(Weight((50..120).random(), 18))
        list.add(Weight((50..120).random(), 19))
        list.add(Weight((50..120).random(), 20))
        list.add(Weight((50..120).random(), 21))
        list.add(Weight((50..120).random(), 22))
        list.add(Weight((50..120).random(), 23))
        list.add(Weight((50..120).random(), 24))
        list.add(Weight((50..120).random(), 25))
        list.add(Weight((50..120).random(), 26))
    }
}
