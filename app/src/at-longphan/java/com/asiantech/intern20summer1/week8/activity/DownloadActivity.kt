package com.asiantech.intern20summer1.week8.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-longphan`.activity_download_w8.*

class DownloadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_w8)
        configStatusBar()

        handleButtonAsyncTaskListener()
        handleButtonThreadHandlerListener()
    }

    private fun handleButtonAsyncTaskListener(){
        btnAsyncTask?.setOnClickListener {
            startActivity(Intent(this, AsyncTaskActivity::class.java))
        }
    }

    private fun handleButtonThreadHandlerListener(){
        btnThreadHandler?.setOnClickListener {
            startActivity(Intent(this, ThreadHandlerActivity::class.java))
        }
    }

    private fun configStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}
