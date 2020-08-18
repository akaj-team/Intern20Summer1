package com.asiantech.intern20summer1.week8

import android.annotation.SuppressLint
import android.app.Activity
import android.os.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.intern20summer1.R
import kotlinx.android.synthetic.`at-phuongle`.activity_download.*
import java.io.*
import java.net.MalformedURLException
import java.net.URL

class DownloadActivity : AppCompatActivity() {
    private val imageUrl: String =
        "https://scontent-hkg4-1.xx.fbcdn.net/v/t31.0-8/p960x960/20507755_748083115381052_9021442773877213840_o.png?_nc_cat=109&_nc_sid=85a577&_nc_ohc=0cswGEWlJkkAX_kC8PB&_nc_ht=scontent-hkg4-1.xx&oh=6cd64e366efc0b006dec0d5ce6a92e12&oe=5F619713"
    private lateinit var handler: Handler

    companion object {
        const val ONE_HUNDRED = 100
        const val BYTE_SIZE = 1024
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)

        init()
    }

    private fun init() {
        handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                progressBar.progress = msg.arg1
                tvPercent.text = msg.arg1.toString().plus(getString(R.string.percent))
            }
        }

        handleHandlerDownloadButtonListener()
        handleAsyncTaskDownloadButtonListener()
    }

    private fun handleHandlerDownloadButtonListener() {
        btnDownloadHandler.setOnClickListener {
            val thread = Thread(Runnable {
                try {
                    val url = URL(imageUrl)
                    val connection = url.openConnection()
                    connection.connect()

                    val fileLength = connection.contentLength
                    val filePath = Environment.getExternalStorageDirectory().path
                    val input: InputStream = BufferedInputStream(url.openStream())
                    val output: OutputStream =
                        FileOutputStream(filePath + getString(R.string.download_file_name))
                    val data = ByteArray(BYTE_SIZE)
                    var total: Long = 0
                    var count: Int

                    while (input.read(data).let {
                            count = it
                            it != -1
                        }) {
                        total += count

                        val msg = handler.obtainMessage()
                        msg.arg1 = (total * ONE_HUNDRED / fileLength).toInt()
                        handler.sendMessage(msg)
                        output.write(data, 0, count)
                    }

                    output.flush()
                    output.close()
                    input.close()

                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            })

            thread.start()
        }
    }

    private fun handleAsyncTaskDownloadButtonListener() {
        btnDownloadAsyncTask.setOnClickListener {
            val downloadAsyncTask = DownloadFile(this)
            downloadAsyncTask.execute(imageUrl)
        }
    }

    private class DownloadFile(context: Activity) :
        AsyncTask<String, Int, String>() {
        private val parentContext: Activity = context

        override fun doInBackground(vararg params: String?): String? {
            try {
                val url = URL(params[0])
                val connection = url.openConnection()
                connection.connect()

                val fileLength = connection.contentLength
                val filePath = Environment.getExternalStorageDirectory().path
                val input: InputStream = BufferedInputStream(url.openStream())
                val output: OutputStream =
                    FileOutputStream(filePath + parentContext.getString(R.string.download_file_name))
                val data = ByteArray(BYTE_SIZE)
                var total: Long = 0
                var count: Int

                while (input.read(data).let {
                        count = it
                        it != -1
                    }) {
                    total += count
                    publishProgress((total * ONE_HUNDRED / fileLength).toInt())
                    output.write(data, 0, count)
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

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            values[0]?.let {
                parentContext.progressBar.progress = it
                parentContext.tvPercent.text =
                    it.toString().plus(parentContext.getString(R.string.percent))

                if (it == 100) {
                    Toast.makeText(parentContext, "Download successfully!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}
