@file:Suppress("DEPRECATION")

package com.asiantech.intern20summer1.w8

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui
 * This is activity class of download activity
 */

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w8.asynctask.DownLoadAsyncTask
import com.asiantech.intern20summer1.w8.handlerthread.DownLoadHandlerThread
import com.asiantech.intern20summer1.w8.handlerthread.DownLoadRunnable
import kotlinx.android.synthetic.`at-huybui`.activity_down_load.*

class DownLoadActivity : AppCompatActivity() {

    companion object {
        private const val NAME_FILE = "New Video"
        private const val EXTENSION = ".mp4"
        private const val LINK_VIDEO =
            """https://r4---sn-8pxuuxa-q5ql.googlevideo.com/videoplayback?expire=1597843419&ei=etM8X6X4OJHs4wLO0a3wBw&ip=2402%3A800%3A621c%3Aeb44%3A4d05%3A895b%3A4efb%3Afea2&id=o-AOnVhidCikyAfPcqzdj9w2A4TULhH76ctuqeDXh_r5-W&itag=22&source=youtube&requiressl=yes&mh=2F&mm=31%2C29&mn=sn-8pxuuxa-q5ql%2Csn-npoe7ney&ms=au%2Crdu&mv=m&mvi=4&pl=48&initcwndbps=1613750&vprv=1&mime=video%2Fmp4&ratebypass=yes&dur=275.086&lmt=1507315472076406&mt=1597821698&fvip=4&fexp=23883098&c=WEB&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cratebypass%2Cdur%2Clmt&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRQIhAKLnahTvAHSkTjdb-IQLokI-TPMrs9IxvO6kyt03uJT3AiAv1Cbqs3phlNFR6Gerw4hJjXZnjgWpo5SlEwI5DGL-4w%3D%3D&sig=AOq0QJ8wRQIgRqAtUhJJD5GztQ-pNFdNuLKgQwn3xTO5oFvH0xuB3pwCIQDjLnsNDObkWfRKqcUOaSYlYMoyfLMR5VkH_JwRu-F-TQ%3D%3D"""
        private const val REGEX_URL =
            """(https://)(.+)"""
        private const val PROGRESS_MAX_VALUE = 100
        private const val TYPE_DATA = "*/*"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_down_load)
        setColorStatusBar()
        initView()
    }

    fun uiAsyncTask(progress: Int) {
        val text = "$progress%"
        tvProgress?.text = text
        progressBar?.progress = progress
    }

    private fun initView() {
        edtUrl?.append(LINK_VIDEO)
        edtNameFile?.append(NAME_FILE)
        edtExtension?.append(EXTENSION)
        initHandlerThreadListener()
        initAsyncTaskListener()
        initOpenFileListener()
    }

    private fun initOpenFileListener() {
        btnOpenFile?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val directory = Environment.DIRECTORY_DOWNLOADS
            val uri: Uri = Uri.parse(directory) // a directory
            intent.setDataAndType(uri, TYPE_DATA)
            startActivity(intent)
        }
    }

    private fun initAsyncTaskListener() {
        btnAsyncTask?.setOnClickListener {
            if (isCheckInternet()) {
                val url = edtUrl.text.toString()
                val name = edtNameFile.text.toString()
                val extension = edtExtension.text.toString()
                if (isCheckEditText(url, name, extension)) {
                    val downLoadAsyncTask = DownLoadAsyncTask(this@DownLoadActivity)
                    downLoadAsyncTask.execute(url, name, extension)
                }
            } else {
                selectInternetDialog()
            }
        }
    }

    private fun initHandlerThreadListener() {
        btnThread?.setOnClickListener {
            if (isCheckInternet()) {
                val url = edtUrl?.text.toString()
                val name = edtNameFile?.text.toString()
                val extension = edtExtension?.text.toString()
                if (isCheckEditText(url, name, extension)) {
                    val uiHandler = UiHandler(this@DownLoadActivity)
                    uiHandler.setTextView(tvProgress, progressBar)
                    val downLoadHandlerThread = DownLoadHandlerThread(uiHandler)
                    downLoadHandlerThread.start()
                    val downLoadRunnable = DownLoadRunnable(downLoadHandlerThread)
                    downLoadRunnable.setParams(url, name, extension)
                    downLoadRunnable.start()
                }
            } else {
                selectInternetDialog()
            }
        }
    }

    private fun isCheckEditText(url: String, name: String, extension: String): Boolean {
        var result = true
        if (name.isEmpty()) {
            Toast.makeText(
                this@DownLoadActivity,
                getString(R.string.w8_text_enter_file_name),
                Toast.LENGTH_SHORT
            ).show()
            result = false
        } else if (extension.isEmpty()) {
            Toast.makeText(
                this@DownLoadActivity,
                getString(R.string.w8_text_enter_extension),
                Toast.LENGTH_SHORT
            ).show()
            result = false
        } else if (!url.matches(REGEX_URL.toRegex())) {
            Toast.makeText(
                this@DownLoadActivity,
                getString(R.string.w8_text_url_invalid),
                Toast.LENGTH_SHORT
            ).show()
            result = false
        }
        return result
    }

    private fun selectInternetDialog() {
        val builder = (this).let { AlertDialog.Builder(it) }
        builder.setTitle(getString(R.string.w8_select_internet))
        val select = arrayOf(
            getString(R.string.w8_select_wifi),
            getString(R.string.w8_select_mobile_network)
        )
        builder.setItems(select) { _, which ->
            when (which) {
                0 -> {
                    startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                }
                1 -> {
                    startActivity(Intent(Settings.ACTION_DATA_ROAMING_SETTINGS))
                }
            }
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun isCheckInternet(): Boolean {
        var returnValue = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val connectivityManager =
                this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        returnValue = true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        returnValue = true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        returnValue = true
                    }
                }
            }
        }
        return returnValue
    }

    class UiHandler(val context: Context) : Handler() {
        private lateinit var weakRefTextView: TextView
        private lateinit var weakRefProgressBar: ProgressBar

        fun setTextView(textView: TextView, progressBar: ProgressBar) {
            weakRefTextView = textView
            weakRefProgressBar = progressBar
        }

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val progress = msg.obj as Int
            if (progress == -1) {
                Toast.makeText(
                    context,
                    context.getString(R.string.w8_dont_download),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val text = "$progress%"
                weakRefTextView.text = text
                weakRefProgressBar.progress = progress
                if (progress == PROGRESS_MAX_VALUE) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.w8_ok_download),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setColorStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.custom_button_icon_stroke)
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility.and(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.custom_button_icon_stroke)
        }
    }
}
