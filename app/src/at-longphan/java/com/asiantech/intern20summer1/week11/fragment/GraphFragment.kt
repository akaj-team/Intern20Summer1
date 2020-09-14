package com.asiantech.intern20summer1.week11.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week11.Weight
import kotlinx.android.synthetic.`at-longphan`.fragment_weight_chart_w11.*
import java.time.YearMonth

class GraphFragment : Fragment() {

    companion object {
        internal fun newInstance() = GraphFragment()
    }

    var list = mutableListOf<Weight>()
    var dayOfMonth = 0
    var month = 9
    var year = 2020

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val yearMonthObject: YearMonth = YearMonth.of(year, month)
        dayOfMonth = yearMonthObject.lengthOfMonth()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weight_chart_w11, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDaysOfMonth.text = "$month/$year: $dayOfMonth days"
        initView()
        initData()
        graphView?.run {
            replaceAllData(list)
            verticalMaxValue = 150
            numberWeek = 3
            horizontalAxisWidth = 90f
            verticalAxisWidth = 20f
            graphTitleSize = 40f
            marginBottom = 20f
            show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun initView() {
        btnChange?.setOnClickListener {
            if (month == 12) {
                month = 1
                year++
            } else month++
            val yearMonthObject: YearMonth = YearMonth.of(year, month)
            dayOfMonth = yearMonthObject.lengthOfMonth()
            tvDaysOfMonth.text = "$month/$year: $dayOfMonth days"
            initData()
            graphView?.run {
                replaceAllData(list)
                verticalMaxValue = 150
                numberWeek = 3
                horizontalAxisWidth = 90f
                verticalAxisWidth = 20f
                graphTitleSize = 40f
                marginBottom = 20f
                show()
            }
        }
    }

    private fun initData() {
        list.clear()
        for (i in 1..dayOfMonth) {
            list.add(Weight((50..120).random(), i))
        }
    }
}
