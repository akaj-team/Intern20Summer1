package com.asiantech.intern20summer1.week8.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-longphan`.activity_download_w8.*

class DownloadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_w8)

        btnAsyncTask.setOnClickListener {
            startActivity(Intent(this, AsyncTaskActivity::class.java))
        }
        /*btnHandler.setOnClickListener {
            startActivity(Intent(this, HandlerThreadActivity::class.java))
        }*/
    }
}