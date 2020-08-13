package com.asiantech.intern20summer1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.w6.activity.StepActivity
import com.asiantech.intern20summer1.w7.activity.SplashActivity
import kotlinx.android.synthetic.`at-sonnguyen`.activity_begin.*

class BeginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_begin)
        initListener()

    }
    private fun initListener(){
        btnWeek6.setOnClickListener {
            val intent = Intent(this,
                StepActivity::class.java)
            startActivity(intent)
            finish()
        }
        btnWeek7.setOnClickListener {
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
