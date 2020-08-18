package com.asiantech.intern20summer1.activity.w8

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.model.DownloadHandlerThread
import kotlinx.android.synthetic.`at-vuongphan`.w8_activity_handler_asynctask.*

class HandlerThreadActivity : AppCompatActivity() {
    private lateinit var handler: Handler

    companion object {
        private const val START_PROGRESS = 100

        private class Handle(private val downloadActivity: HandlerThreadActivity) : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == START_PROGRESS) {
                    downloadActivity.progressBar.progress = msg.arg1
                    downloadActivity.tvRatioNumber.text = msg.arg1.toString()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w8_activity_handler_asynctask)
        btnStart.setOnClickListener {
            progressBar.progress = 0
            if (progressBar.progress == 0) {
                Toast.makeText(this, "starting", Toast.LENGTH_SHORT).show()
            }
            tvRatioNumber.text = getString(R.string.text_view_text_0)
            Thread(
                DownloadHandlerThread(
                    this,
                    getString(R.string.edt_text_link_download),
                    handler
                )
            ).start()
        }
    }

    override fun onResume() {
        super.onResume()
        handler =
            Handle(
                this
            )
    }
}
