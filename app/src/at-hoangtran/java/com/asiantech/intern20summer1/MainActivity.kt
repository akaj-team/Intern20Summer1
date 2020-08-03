package com.asiantech.intern20summer1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.week3.LayoutActivity
import com.asiantech.intern20summer1.week6.IndicatorActivity
import kotlinx.android.synthetic.`at-hoangtran`.activity_main2.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        handleWeek4Button()
        handleWeek6Button()
    }

    private fun handleWeek4Button() {
        btn_week4.setOnClickListener {
            val intent = Intent(this, LayoutActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

//    private fun handleWeek5Button() {
//        btn_week5.setOnClickListener {
//            val intent = Intent(this, LayoutActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }

    private fun handleWeek6Button() {
        btn_week6.setOnClickListener {
            val intent = Intent(this, IndicatorActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
