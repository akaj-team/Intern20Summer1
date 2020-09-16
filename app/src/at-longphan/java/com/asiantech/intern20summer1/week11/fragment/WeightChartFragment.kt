package com.asiantech.intern20summer1.week11.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week11.Weight
import com.asiantech.intern20summer1.week11.customview.WeightChart
import kotlinx.android.synthetic.`at-longphan`.fragment_weight_chart_w11.*
import kotlin.random.Random
import kotlin.random.nextInt

class WeightChartFragment : Fragment() {

    companion object {
        internal fun newInstance() = WeightChartFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weight_chart_w11, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weights = mutableListOf<Weight>()
        var maxWeight: Int = 0
        for (i in 1..WeightChart.SIZE_WEIGHT) {
            val random = Random.nextInt(WeightChart.MIN_WEIGHT..WeightChart.MAX_WEIGHT)
            if (random > maxWeight) {
                maxWeight = random + WeightChart.EXTRA_BOUND
            }
            weights.add(Weight(random, i))
        }
        weightChartW11?.initData(weights, maxWeight)
    }
}
