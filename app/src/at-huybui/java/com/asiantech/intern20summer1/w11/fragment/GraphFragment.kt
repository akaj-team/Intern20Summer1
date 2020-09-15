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
 * This is GraphFragment class fof graph fragment.
 * It will contain graph weight
 */
class GraphFragment : Fragment() {

    companion object {
        private const val MAX_VALUE = 100
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
        graphView?.run {
            replaceAllData(list)
            verticalMaxValue = 150
            numberWeek = 3
            horizontalAxisWidth = 90f
            marginBottom = 20f
            marginLeft = 50f
            show()
        }
        btnChange?.setOnClickListener {
            initData()
            graphView?.run {
                replaceAllData(list)
                verticalMaxValue = 150
                numberWeek = 3
                horizontalAxisWidth = 90f
                marginBottom = 20f
                marginLeft = 50f
                show()
            }
        }
    }

    private fun initData() {
        list.clear()
        for (i in 1..MAX_VALUE) {
            list.add(Weight((50..120).random(), i))
        }
    }
}
