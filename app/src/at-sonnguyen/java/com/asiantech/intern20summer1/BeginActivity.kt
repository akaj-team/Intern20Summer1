package com.asiantech.intern20summer1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.w11.activity.HomeActivity
import com.asiantech.intern20summer1.w6.activity.StepActivity
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
        handleWeek11ButtonListener()
    }
    private fun handleWeek11ButtonListener(){
        btnWeek11.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
