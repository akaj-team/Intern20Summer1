package com.asiantech.intern20summer1.week8

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-hoangtran`.activity_download_file.*

class DownloadFileActivity : AppCompatActivity() {
    companion object {
        internal const val FILE_URL =
            "https://www.iconfinder.com/icons/211613/download/png/512"

        internal const val FILE_NAME = "download_image"
        internal const val FILE_TAIL = ".png"
        internal const val BYTE_ARRAY_SIZE = 1024
        internal const val TIME_PERIOD = 100
    }

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_file)

        handleMessage()
        handleAsyncTaskButton()
        handleTreadButton()
    }

    private fun handleMessage() {
        handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == TIME_PERIOD) {
                    progressBar.progress = msg.arg1
                    tvProgress.text =
                        msg.arg1.toString() + getString(R.string.percent)
                    if (msg.arg1.toString() == "$TIME_PERIOD") {
                        Toast.makeText(
                            this@DownloadFileActivity,
                            R.string.download_complete,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun handleTreadButton() {
        btnThreadHandler.setOnClickListener {
            progressBar.progress = 0
            Thread(ThreadHandler(this, FILE_URL, handler)).start()
            tvProgress.text = resources.getString(R.string.text_view_progress_default)
        }
    }

    private fun handleAsyncTaskButton() {
        btnAsyncTask.setOnClickListener {
            progressBar.progress = 0
            AsyncTask(this, progressBar, tvProgress).execute(FILE_URL)
            tvProgress.text = resources.getString(R.string.text_view_progress_default)
        }
    }
}
