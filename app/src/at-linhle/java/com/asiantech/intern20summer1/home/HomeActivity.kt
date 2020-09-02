package com.asiantech.intern20summer1.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week10.views.LoginMainActivity
import com.asiantech.intern20summer1.week3.MainActivity
import com.asiantech.intern20summer1.week4.views.SplashActivity
import com.asiantech.intern20summer1.week6.views.IndicatorActivity
import kotlinx.android.synthetic.`at-linhle`.activity_container.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        initListeners()
    }

    private fun initListeners() {
        weekThree?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        weekFour?.setOnClickListener {
            startActivity(Intent(this, SplashActivity::class.java))
        }

        weekSix?.setOnClickListener {
            startActivity(Intent(this, IndicatorActivity::class.java))
        }

        weekTen?.setOnClickListener {
            startActivity(Intent(this, LoginMainActivity::class.java))
        }
    }
}
