package com.asiantech.intern20summer1

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.week3.LoginActivity
import com.asiantech.intern20summer1.week4.activity.SplashActivity
import com.asiantech.intern20summer1.week5.activity.TimeLineActivity
import com.asiantech.intern20summer1.week6.activity.FirstPageActivity
import com.asiantech.intern20summer1.week7.activity.SplashWeekSevenActivity
import com.asiantech.intern20summer1.week9.MainActivityWeekNine
import kotlinx.android.synthetic.`at-longphan`.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configStatusBar()
        handleButtonsClick()
    }

    private fun configStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun handleButtonsClick() {
        handleButtonWeek3()
        handleButtonWeek4()
        handleButtonWeek5()
        handleButtonWeek6()
        handleButtonWeek7()
        handleButtonWeek9()
    }

    private fun handleButtonWeek3() {
        btnWeek3?.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun handleButtonWeek4() {
        btnWeek4?.setOnClickListener {
            startActivity(Intent(this, SplashActivity::class.java))
        }
    }

    private fun handleButtonWeek5() {
        btnWeek5?.setOnClickListener {
            startActivity(Intent(this, TimeLineActivity::class.java))
        }
    }

    private fun handleButtonWeek6() {
        btnWeek6?.setOnClickListener {
            startActivity(Intent(this, FirstPageActivity::class.java))
        }
    }

    private fun handleButtonWeek7() {
        btnWeek7?.setOnClickListener {
            startActivity(Intent(this, SplashWeekSevenActivity::class.java))
        }
    }

    private fun handleButtonWeek9() {
        btnWeek9?.setOnClickListener {
            startActivity(Intent(this, MainActivityWeekNine::class.java))
        }
    }
}
