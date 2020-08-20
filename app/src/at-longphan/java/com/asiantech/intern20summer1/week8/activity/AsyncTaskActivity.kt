package com.asiantech.intern20summer1.week8.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week8.asynctask.DownloadAsyncTask
import kotlinx.android.synthetic.`at-longphan`.activity_async_task_w8.*

class AsyncTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_task_w8)

        btnStart?.setOnClickListener {
            progressBar?.progress = 0
            //tvRatioNumber.text = getString(R.string.text_view_text_0)
            tvRatioNumber?.text = "0"
            tvRatio?.text = "%"
            DownloadAsyncTask(
                this,
                progressBar,
                tvRatioNumber
            )
                //.execute(getString(R.string.edt_text_link_download))
                .execute("https://images.unsplash.com/photo-1533461502717-83546f485d24?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60")
        }
    }
}