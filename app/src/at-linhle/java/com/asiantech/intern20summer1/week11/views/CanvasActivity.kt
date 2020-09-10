package com.asiantech.intern20summer1.week11.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week11.fragments.ChessBoardFragment
import com.asiantech.intern20summer1.week11.fragments.WeightChartFragment
import kotlinx.android.synthetic.`at-linhle`.activity_canvas.*

class CanvasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)
        handleClickingListener()
    }

    private fun handleClickingListener() {
        btnChessBoard.setOnClickListener {
            openFragment(ChessBoardFragment.newInstance())
        }
        btnWeightChart.setOnClickListener {
            openFragment(WeightChartFragment.newInstance())
        }
    }

    private fun openFragment(fragment: Fragment) {
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.flCanvasContainer, fragment).commit()
    }
}
