package com.asiantech.intern20summer1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.w12.activity.LoginActivity
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
        handleWeek12ButtonListener()
    }

    private fun handleWeek12ButtonListener(){
        btnWeek12.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
