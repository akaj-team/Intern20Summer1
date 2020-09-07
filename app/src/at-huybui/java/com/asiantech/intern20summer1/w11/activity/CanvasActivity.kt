package com.asiantech.intern20summer1.w11.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w11.fragment.ChessBoardFragment
import com.asiantech.intern20summer1.w11.fragment.GraphFragment
import kotlinx.android.synthetic.`at-huybui`.w11_activity_canvas.*

/**
 * Asian Tech Co., Ltd.
 * Intern20Summer1 Project.
 * Created by at-huybui on 07/09/2020.
 * This is xxx TODO("Not yet implemented").
 * It will TODO("Not yet implemented")
 */

class CanvasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w11_activity_canvas)
        setColorStatusBar()
        initListener()
        replaceFragment(ChessBoardFragment.newInstance())
    }

    private fun initListener() {
        btnExerciseOne?.setOnClickListener {
            replaceFragment(ChessBoardFragment.newInstance(), true)
        }

        btnExerciseTwo?.setOnClickListener {
            replaceFragment(GraphFragment.newInstance(), true)
        }
    }

    private fun replaceFragment(fragment: Fragment, backStack: Boolean = false) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutContainer, fragment)
        if (backStack) fragmentTransaction.addToBackStack("")
        fragmentTransaction.commit()
    }

    private fun setColorStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.apply {
                window.statusBarColor = ContextCompat.getColor(this, R.color.background_white)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    window.setDecorFitsSystemWindows(false)
                } else {
                    @Suppress("DEPRECATION")
                    window.decorView.systemUiVisibility =
                        window.decorView.systemUiVisibility.or(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                }
            }
        }
    }
}
