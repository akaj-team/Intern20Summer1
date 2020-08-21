package com.asiantech.intern20summer1.week8.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week8.asynctask.DownloadImageAsyncTask
import kotlinx.android.synthetic.`at-longphan`.activity_async_task_w8.*

class AsyncTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_task_w8)
        configStatusBar()

        initView()
        handlerButtonDownloadImageListener()
    }

    private fun initView(){
        edtImageUrl?.setText(R.string.default_image_url_w8)
    }

    private fun handlerButtonDownloadImageListener(){
        btnDownloadImageW8?.setOnClickListener {
            progressBarW8?.progress = 0
            tvDownloadedPercentW8?.visibility = View.VISIBLE
            tvPercentW8?.visibility = View.VISIBLE
            DownloadImageAsyncTask(
                this,
                progressBarW8,
                tvDownloadedPercentW8
            )
                .execute(edtImageUrl?.text.toString())
        }
    }

    private fun configStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}
