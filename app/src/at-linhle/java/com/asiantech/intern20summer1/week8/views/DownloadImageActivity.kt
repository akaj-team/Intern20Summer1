package com.asiantech.intern20summer1.week8.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week8.extentsions.ThreadHandler
import com.asiantech.intern20summer1.week8.extentsions.ThreadHandler.Companion.TIME_PERIOD
import kotlinx.android.synthetic.`at-linhle`.activity_dowload_image.*

class DownloadImageActivity : AppCompatActivity() {
    companion object {
        private const val IMAGE_URL =
            "https://upload.wikimedia.org/wikipedia/commons/5/55/Apple_orchard_in_Tasmania.jpg"
    }

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dowload_image)
        handleMessage()
        handleButtonClickedListener()
    }

    private fun handleMessage() {
        handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == TIME_PERIOD) {
                    progressBar.progress = msg.arg1
                    tvPercentNumber.text = msg.arg1.toString()
                }
            }
        }
    }

    private fun handleButtonClickedListener() {
        btnThreadHandler.setOnClickListener {
            progressBar.progress = 0
            tvPercentNumber.text = getString(R.string.percent_number_text_view_start_description)
            Thread(ThreadHandler(this, IMAGE_URL, handler)).start()
        }
    }
}
