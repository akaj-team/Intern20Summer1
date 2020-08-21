package com.asiantech.intern20summer1.week8.activity

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week8.handler.DownloadImageHandler
import kotlinx.android.synthetic.`at-longphan`.activity_async_task_w8.*

class ThreadHandlerActivity : AppCompatActivity() {

    private lateinit var handler: Handler

    companion object {
        private class Handle(private val activity: ThreadHandlerActivity) : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == DownloadImageHandler.DOWNLOAD_IMAGE_MSG_CODE) {
                    activity.progressBarW8.progress = msg.arg1
                    activity.tvDownloadedPercentW8.text = msg.arg1.toString()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_task_w8)
        configStatusBar()

        initView()
        handleButtonDownloadImageListener()
    }

    override fun onResume() {
        super.onResume()
        handler = Handle(this)
    }

    private fun initView(){
        edtImageUrl?.setText(R.string.default_image_url_w8)
    }

    private fun handleButtonDownloadImageListener(){
        btnDownloadImageW8?.setOnClickListener {
            progressBarW8?.progress = 0
            if (progressBarW8?.progress == 0) {
                Toast.makeText(this, getString(R.string.toast_starting_download_w8), Toast.LENGTH_SHORT).show()
            }

            tvDownloadedPercentW8?.visibility = View.VISIBLE
            tvPercentW8?.visibility = View.VISIBLE

            Thread(
                DownloadImageHandler(
                    this,
                    edtImageUrl?.text.toString(),
                    handler
                )
            ).start()
        }
    }

    private fun configStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}
