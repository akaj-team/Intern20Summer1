package com.asiantech.intern20summer1.activity.w8

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.model.DownloadAsyncTask
import kotlinx.android.synthetic.`at-vuongphan`.w8_activity_handler_asynctask.*

class AsyncTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w8_activity_handler_asynctask)
        btnStart.setOnClickListener {
            progressBar.progress = 0
            tvRatioNumber.text = getString(R.string.text_view_text_0)
            DownloadAsyncTask(
                this,
                progressBar,
                tvRatioNumber
            ).execute(getString(R.string.edt_text_link_download))
        }
    }
}
