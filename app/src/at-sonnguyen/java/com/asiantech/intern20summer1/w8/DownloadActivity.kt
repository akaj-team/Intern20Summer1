package com.asiantech.intern20summer1.w8
import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-sonnguyen`.w8_activity_download.*
import java.io.*
import java.net.MalformedURLException
class DownloadActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    companion object {
        private const val URL =
            "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/b19ee0b0-994e-4049-8482-f22ee6a446ad/db5bwru-0b113141-d96f-4330-b346-e696b35de112.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOiIsImlzcyI6InVybjphcHA6Iiwib2JqIjpbW3sicGF0aCI6IlwvZlwvYjE5ZWUwYjAtOTk0ZS00MDQ5LTg0ODItZjIyZWU2YTQ0NmFkXC9kYjVid3J1LTBiMTEzMTQxLWQ5NmYtNDMzMC1iMzQ2LWU2OTZiMzVkZTExMi5qcGcifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6ZmlsZS5kb3dubG9hZCJdfQ.vIIiGdrBxji-flXb7YlIpgHBvNpTJTxOub5r69MeE2M"
        internal const val NAME_FILE_IMAGE_KEY = "Download"
        internal const val FILE_TAIL_KEY = ".jpg"
        internal const val PERCENT_MAX = 100
        internal const val BYTE_ARRAY_SIZE = 1024
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w8_activity_download)
        handleMessage()
        initListener()
    }
    private fun handleMessage() {
        handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            @SuppressLint("SetTextI18n")
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == PERCENT_MAX) {
                    progressBarDownloadProcess.progress = msg.arg1
                    tvProcessPercent.text =
                        msg.arg1.toString() + resources.getString(R.string.w8_percent_text)
                    if (msg.arg1.toString() == PERCENT_MAX.toString()) {
                        Toast.makeText(
                            this@DownloadActivity,
                            resources.getString(R.string.w8_download_success_toast_text),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        }
    }
    private fun initListener() {
        handleThreadHandlerButtonListener()
        handleAsyncTaskButtonListener()
    }
    private fun handleThreadHandlerButtonListener() {
        btnThreadHandlerDownload.setOnClickListener {
            progressBarDownloadProcess.progress = 0
            tvProcessPercent.text = resources.getString(R.string.w8_percent_text_view_default_text)
            Thread(ThreadHandler(this, URL, handler)).start()
        }
    }
    private fun handleAsyncTaskButtonListener() {
        btnAsyncTaskDownload.setOnClickListener {
            progressBarDownloadProcess.progress = 0
            tvProcessPercent.text = resources.getString(R.string.w8_percent_text_view_default_text)
            AsyncTaskDownload(this, progressBarDownloadProcess, tvProcessPercent).execute(URL)
        }
    }
    class AsyncTaskDownload(
        private val context: Context,
        private val progressBar: ProgressBar,
        private val textView: TextView
    ) : AsyncTask<String, String, String>() {
        private var directory: File? = null
        override fun doInBackground(vararg params: String?): String? {
            try {
                directory = context.getDir(NAME_FILE_IMAGE_KEY, Context.MODE_PRIVATE)
                val path = File(directory, FILE_TAIL_KEY)
                var count: Int
                var total = 0
                val url = java.net.URL(params[0])
                val connection = url.openConnection()
                connection.connect()
                val fileLength = connection.contentLength
                val input: InputStream = BufferedInputStream(url.openStream())
                val output = FileOutputStream(path)
                val data = ByteArray(BYTE_ARRAY_SIZE)
                while (input.read(data).let {
                        count = it
                        it != -1
                    }) {
                    total += count
                    output.write(data, 0, count)
                    publishProgress("${total * PERCENT_MAX / fileLength}")
                }
                output.flush()
                output.close()
                input.close()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }
        @SuppressLint("SetTextI18n")
        override fun onProgressUpdate(vararg values: String?) {
            super.onProgressUpdate(*values)
            values[0]?.let { progressBar.progress = it.toInt() }
            textView.text = values[0] + context.getString(R.string.w8_percent_text)
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Toast.makeText(
                context,
                context.getString(R.string.w8_download_success_toast_text),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    class ThreadHandler(val context: Context, private val url: String, val handler: Handler) :
        Runnable {
        private var directory: File? = null
        override fun run() {
            try {
                directory = context.getDir(NAME_FILE_IMAGE_KEY, Context.MODE_PRIVATE)
                val path = File(directory, FILE_TAIL_KEY)
                var count: Int
                var total = 0
                val url = java.net.URL(url)
                val connection = url.openConnection()
                connection.connect()
                val fileLength = connection.contentLength
                val input: InputStream = BufferedInputStream(url.openStream())
                val output = FileOutputStream(path)
                val data = ByteArray(BYTE_ARRAY_SIZE)
                while (input.read(data).let {
                        count = it
                        it != -1
                    }) {
                    total += count
                    output.write(data, 0, count)
                    val msg = Message()
                    msg.what = PERCENT_MAX
                    msg.arg1 = (total * PERCENT_MAX / fileLength)
                    handler.sendMessage(msg)
                }
                output.flush()
                output.close()
                input.close()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}