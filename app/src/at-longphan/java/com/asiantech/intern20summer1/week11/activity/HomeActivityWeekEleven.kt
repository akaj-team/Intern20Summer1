package com.asiantech.intern20summer1.week11.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week11.fragment.ChessTableFragment
import kotlinx.android.synthetic.`at-longphan`.activity_home_w11.*

class HomeActivityWeekEleven : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_w11)

        handleListeners()
    }

    private fun handleListeners() {
        btnChessTable?.setOnClickListener {
            replaceFragment(ChessTableFragment(), true)
        }
        btnWeightTracking?.setOnClickListener {
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
}