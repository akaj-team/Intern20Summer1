package com.asiantech.intern20summer1.w8

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.util.Log.d
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w8.asynctask.DownLoadAsyncTask
import com.asiantech.intern20summer1.w8.handlerthread.DownLoadHandlerThread
import com.asiantech.intern20summer1.w8.handlerthread.DownLoadRunnable
import kotlinx.android.synthetic.`at-huybui`.activity_down_load.*


class DownLoadActivity : AppCompatActivity() {

    companion object {
        private const val REGEX_URL =
            """(https:\/\/)[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?"""
    }

    private lateinit var uiHandler: UiHandler
    private lateinit var downLoadHandlerThread: DownLoadHandlerThread
    private lateinit var downLoadRunnable: DownLoadRunnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_down_load)
    }

    override fun onStart() {
        super.onStart()
        initListener()
    }

    private fun initListener() {
        initHandlerThreadListener()
        initAsyncTaskListener()
        initOpenFileListener()
    }

    private fun initOpenFileListener() {
        btnOpenFile?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val directory = Environment.DIRECTORY_DOWNLOADS
            d("XXXX", "[path]: $directory")
            val uri: Uri = Uri.parse(directory) // a directory
            intent.setDataAndType(uri, "*/*")
            startActivity(intent)
        }
    }

    private fun initAsyncTaskListener() {
        btnAsyncTask?.setOnClickListener {
            val url = edtUrl.text.toString()
            val name = edtNameFile.text.toString()
            val extension = edtExtension.text.toString()
            if (isCheckEditText(url, name, extension)) {
                val downLoadAsyncTask = DownLoadAsyncTask(this@DownLoadActivity)
                downLoadAsyncTask.execute(url, name, extension)
            }
        }
    }

    private fun initHandlerThreadListener() {
        btnThread?.setOnClickListener {
            val url = edtUrl?.text.toString()
            val name = edtNameFile?.text.toString()
            val extension = edtExtension?.text.toString()
            uiHandler = UiHandler()
            uiHandler.setTextView(tvProgress, progressBar)
            downLoadHandlerThread = DownLoadHandlerThread(uiHandler)
            downLoadHandlerThread.start()
            downLoadRunnable = DownLoadRunnable(downLoadHandlerThread)
            downLoadRunnable.setParams(url, name, extension)
            downLoadRunnable.start()
        }
    }

    fun uiAsyncTask(progress: Int) {
        val text = "$progress%"
        tvProgress?.text = text
        progressBar?.progress = progress
    }

    private fun isCheckEditText(url: String, name: String, extension: String): Boolean {
        var result = true
        if (name.isEmpty()) {
            Toast.makeText(this@DownLoadActivity, "Hãy nhập tên file", Toast.LENGTH_SHORT)
                .show()
            result = false
        } else if (extension.isEmpty()) {
            Toast.makeText(this@DownLoadActivity, "Hãy nhập extension", Toast.LENGTH_SHORT)
                .show()
            result = false
        } else if (!url.matches(REGEX_URL.toRegex())) {
            Toast.makeText(this@DownLoadActivity, "Url không hợp lệ", Toast.LENGTH_SHORT).show()
            result = false
        }
        return result
    }

    class UiHandler : Handler() {
        private lateinit var weakRefTextView: TextView
        private lateinit var weakRefProgressBar: ProgressBar

        fun setTextView(textView: TextView, progressBar: ProgressBar) {
            weakRefTextView = textView
            weakRefProgressBar = progressBar
        }

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val progress = msg.obj as Int
            val text = "$progress%"
            weakRefTextView.text = text
            weakRefProgressBar.progress = progress
        }
    }
}
