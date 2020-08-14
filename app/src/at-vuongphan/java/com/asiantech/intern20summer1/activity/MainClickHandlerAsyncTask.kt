package com.asiantech.intern20summer1.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-vuongphan`.w8_main_activity.*

class MainClickHandlerAsyncTask : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w8_main_activity)
        btnAsyncTask.setOnClickListener {
            startActivity(Intent(this, AsyncTaskActivity::class.java))
        }
        btnHandler.setOnClickListener {
            startActivity(Intent(this, HandlerThreadActivity::class.java))
        }
    }
}
