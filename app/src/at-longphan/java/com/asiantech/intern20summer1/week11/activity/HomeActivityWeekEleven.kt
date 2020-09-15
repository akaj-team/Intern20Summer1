package com.asiantech.intern20summer1.week11.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week11.fragment.ChessTableFragment
import com.asiantech.intern20summer1.week11.fragment.WeightChartFragment
import kotlinx.android.synthetic.`at-longphan`.activity_home_w11.*

class HomeActivityWeekEleven : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_w11)
        configStatusBar()
        handleListeners()
    }

    private fun handleListeners() {
        btnChessTable?.setOnClickListener {
            replaceFragment(ChessTableFragment(), true)
        }
        btnWeightTracking?.setOnClickListener {
            replaceFragment(WeightChartFragment(), true)
        }
    }

    private fun replaceFragment(fragment: Fragment, isAddToBackStack: Boolean = false) {
        val transaction =
            supportFragmentManager.beginTransaction().replace(R.id.containerHomeW11, fragment, null)
        transaction.setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        if (isAddToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    private fun configStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}